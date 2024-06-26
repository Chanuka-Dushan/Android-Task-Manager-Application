package com.example.madtaskapp.fragment

import android.os.Bundle
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
import com.example.madtaskapp.databinding.FragmentAddTaskBinding
import com.example.madtaskapp.model.Task
import com.example.madtaskapp.viewmodel.TaskViewMode


class AddTaskFragment : Fragment(R.layout.fragment_add_task),MenuProvider {
    private var addTaskBinding:FragmentAddTaskBinding?=null
    private val binding get() = addTaskBinding!!

    private lateinit var taskViewModel: TaskViewMode
    private lateinit var addTaskView:View



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_add_task, container, false)
        addTaskBinding=FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel=(activity as MainActivity).taskViewModel
        addTaskView=view

    }
    private fun saveTask(view: View){
        val taskTitle=binding.addTaskTitle.text.toString().trim()
        val taskDesc=binding.addTaskDesc.text.toString().trim()

        if (taskTitle.isNotEmpty()){
            val task=Task(0,taskTitle,taskDesc)
            taskViewModel.addTask(task)

            Toast.makeText(addTaskView.context,"Task Saved",Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addTaskView.context,"Please Enter a Task Title",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.saveMenu->{
                saveTask(addTaskView)
                true
            }else->false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addTaskBinding=null
    }

}