package com.arthur.taskflow

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.arthur.taskflow.data.local.TaskDao
import com.arthur.taskflow.data.local.TaskDatabase
import com.arthur.taskflow.model.Task

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var database: TaskDatabase

    private lateinit var taskDao: TaskDao


    @Before
    fun setup() {

        val context =
            InstrumentationRegistry
                .getInstrumentation()
                .targetContext

        database =
            Room.inMemoryDatabaseBuilder(
                context,
                TaskDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()

        taskDao =
            database.taskDao()
    }


    @After
    fun tearDown() {

        database.close()
    }


    @Test
    fun insertTask_returnsInsertedTask() =
        runBlocking {

            val task =
                Task(
                    title = "Estudar Kotlin"
                )

            taskDao.insertTask(task)


            val tasks =
                taskDao
                    .getAllTasks()
                    .first()


            assertEquals(
                1,
                tasks.size
            )


            assertEquals(
                "Estudar Kotlin",
                tasks.first().title
            )
        }

    @Test
    fun updateTaskCompleted_changesCompletedState() =
        runBlocking {

            val task = Task(
                title = "Estudar Android"
            )

            taskDao.insertTask(task)

            val insertedTask =
                taskDao
                    .getAllTasks()
                    .first()
                    .first()

            assertEquals(
                false,
                insertedTask.completed
            )

            taskDao.updateTaskCompleted(
                taskId = insertedTask.id,
                completed = true
            )

            val updatedTask =
                taskDao
                    .getAllTasks()
                    .first()
                    .first()

            assertEquals(
                true,
                updatedTask.completed
            )
        }

    @Test
    fun deleteTask_removesTaskFromDatabase() =
        runBlocking {

            val task = Task(
                title = "Excluir esta tarefa"
            )

            taskDao.insertTask(task)

            val insertedTask =
                taskDao
                    .getAllTasks()
                    .first()
                    .first()

            taskDao.deleteTaskById(
                insertedTask.id
            )

            val tasks =
                taskDao
                    .getAllTasks()
                    .first()

            assertEquals(
                0,
                tasks.size
            )
        }


}