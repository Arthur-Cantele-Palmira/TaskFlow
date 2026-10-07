package com.arthur.taskflow.data.remote

import com.arthur.taskflow.model.TodoDto
import retrofit2.http.GET

interface TodoApi {

    @GET("todos")
    suspend fun getTodos(): List<TodoDto>
}