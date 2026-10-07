package com.arthur.taskflow.model

sealed interface RemoteTodoUiState {

    data object Idle : RemoteTodoUiState

    data object Loading : RemoteTodoUiState

    data class Success(
        val todos: List<TodoDto>
    ) : RemoteTodoUiState

    data class Error(
        val message: String
    ) : RemoteTodoUiState
}