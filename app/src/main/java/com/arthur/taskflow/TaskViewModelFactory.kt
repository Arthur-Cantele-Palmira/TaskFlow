package com.arthur.taskflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arthur.taskflow.data.TaskRepository

class TaskViewModelFactory(
    private val repository: TaskRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                TaskViewModel::class.java
            )
        ) {

            return TaskViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconhecido"
        )
    }
}