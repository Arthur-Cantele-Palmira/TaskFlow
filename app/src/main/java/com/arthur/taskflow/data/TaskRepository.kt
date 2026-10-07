package com.arthur.taskflow.data

import com.arthur.taskflow.data.local.TaskDao
import com.arthur.taskflow.data.remote.RetrofitClient
import com.arthur.taskflow.model.Task
import com.arthur.taskflow.model.TodoDto
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {

    val tasks: Flow<List<Task>> =
        taskDao.getAllTasks()

    suspend fun addTask(title: String) {

        val normalizedTitle = title.trim()

        if (normalizedTitle.isNotEmpty()) {

            val task = Task(
                title = normalizedTitle
            )

            taskDao.insertTask(task)
        }
    }

    suspend fun updateTaskCompleted(
        taskId: Int,
        completed: Boolean
    ) {
        taskDao.updateTaskCompleted(
            taskId = taskId,
            completed = completed
        )
    }

    suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

    suspend fun getRemoteTodos(): List<TodoDto> {
        return RetrofitClient.todoApi.getTodos()
    }
}