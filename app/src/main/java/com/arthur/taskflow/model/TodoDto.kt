package com.arthur.taskflow.model

data class TodoDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)