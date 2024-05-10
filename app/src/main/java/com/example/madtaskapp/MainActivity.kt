package com.example.madtaskapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.madtaskapp.database.TaskDatabase
import com.example.madtaskapp.repository.TaskRepository
import com.example.madtaskapp.viewmodel.TaskViewMode
import com.example.madtaskapp.viewmodel.TaskViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var taskViewModel: TaskViewMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModel()

    }
    private fun setupViewModel(){
        val taskRepository=TaskRepository(TaskDatabase(this))
        val viewModelProviderFactory=TaskViewModelFactory(application,taskRepository)
        taskViewModel= ViewModelProvider(this,viewModelProviderFactory)[TaskViewMode::class.java]
    }
}