package com.arthur.taskflow

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.taskflow.data.TaskRepository
import com.arthur.taskflow.model.RemoteTodoUiState
import com.arthur.taskflow.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks: Flow<List<Task>> =
        repository.tasks


    private val _remoteUiState =
        mutableStateOf<RemoteTodoUiState>(
            RemoteTodoUiState.Idle
        )

    val remoteUiState: State<RemoteTodoUiState> =
        _remoteUiState


    fun addTask(title: String) {

        viewModelScope.launch {
            repository.addTask(title)
        }
    }


    fun updateTaskCompleted(
        taskId: Int,
        completed: Boolean
    ) {

        viewModelScope.launch {

            repository.updateTaskCompleted(
                taskId = taskId,
                completed = completed
            )
        }
    }


    fun deleteTask(taskId: Int) {

        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }


    fun loadRemoteTodos() {

        viewModelScope.launch {

            _remoteUiState.value =
                RemoteTodoUiState.Loading

            try {

                val todos =
                    repository.getRemoteTodos()

                _remoteUiState.value =
                    RemoteTodoUiState.Success(
                        todos = todos
                    )

            } catch (e: Exception) {

                _remoteUiState.value =
                    RemoteTodoUiState.Error(
                        message =
                            e.message
                                ?: "Erro ao carregar tarefas"
                    )
            }
        }
    }
}