package com.arthur.taskflow.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arthur.taskflow.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    suspend fun insertTask(task: Task)

    @Query(
        """
        UPDATE tasks
        SET completed = :completed
        WHERE id = :taskId
        """
    )
    suspend fun updateTaskCompleted(
        taskId: Int,
        completed: Boolean
    )

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: Int)
}