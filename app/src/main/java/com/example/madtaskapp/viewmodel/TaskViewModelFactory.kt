package com.example.madtaskapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.madtaskapp.repository.TaskRepository

class TaskViewModelFactory(val app:Application,private val taskRepository:TaskRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewMode(app,taskRepository) as T
    }
}