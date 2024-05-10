package com.example.madtaskapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.madtaskapp.MainActivity
import com.example.madtaskapp.R

import com.example.madtaskapp.databinding.FragmentEditTaskBinding
import com.example.madtaskapp.model.Task
import com.example.madtaskapp.viewmodel.TaskViewMode


class EditTaskFragment : Fragment(R.layout.fragment_edit_task),MenuProvider {
    private var editTaskBinding:FragmentEditTaskBinding?=null
    private val binding get() = editTaskBinding!!
    private lateinit var taskViewModel: TaskViewMode
    private lateinit var currentTask:Task


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editTaskBinding=FragmentEditTaskBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel

        // Retrieving the arguments safely
        currentTask = requireArguments().getParcelable("task")!!



        binding.editTaskTitle.setText(currentTask.taskTitle)
        binding.editTaskDesc.setText(currentTask.taskDesc)

        binding.editTaskFab.setOnClickListener {
            val taskTitle = binding.editTaskTitle.text.toString().trim()
            val taskDescription = binding.editTaskDesc.text.toString().trim()

            if (taskTitle.isNotEmpty()) {
                val updatedTask = Task(currentTask.id, taskTitle, taskDescription)
                taskViewModel.updateTask(updatedTask)
                view.findNavController().popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Please Enter Task Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteTask(){
        AlertDialog.Builder(activity).apply{
            setTitle("Delete Task")
            setMessage("Do you want to Delete this Task ?")
            setPositiveButton("Delete"){_,_ ->
                taskViewModel.deleteTask(currentTask)
                Toast.makeText(context,"Task Deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)
            }
            setNegativeButton("Cancel",null)

        }.create().show()

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.edit_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteTask()
                true
            }else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editTaskBinding=null
    }

}