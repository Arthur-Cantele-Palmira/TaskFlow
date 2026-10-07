package com.arthur.taskflow

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.platform.app.InstrumentationRegistry
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import com.arthur.taskflow.data.TaskRepository
import com.arthur.taskflow.data.local.TaskDatabase
import com.arthur.taskflow.ui.theme.TaskFlowTheme
import kotlinx.coroutines.flow.first

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TaskScreenTest {

    @get:Rule
    val composeTestRule =
        createComposeRule()


    private lateinit var database: TaskDatabase

    private lateinit var repository: TaskRepository

    private lateinit var taskViewModel: TaskViewModel


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


        repository =
            TaskRepository(
                taskDao = database.taskDao()
            )


        taskViewModel =
            TaskViewModel(
                repository = repository
            )


        composeTestRule.setContent {

            TaskFlowTheme {

                TaskScreen(
                    taskViewModel = taskViewModel,

                    onOpenCompleted = {},

                    onOpenRemote = {}
                )
            }
        }
    }


    @After
    fun tearDown() {

        database.close()
    }


    @Test
    fun addTask_displaysTaskOnScreen() {

        composeTestRule
            .onNodeWithText("Nova tarefa")
            .performTextInput("Estudar Kotlin")


        composeTestRule
            .onNodeWithText("Adicionar")
            .performClick()


        composeTestRule.waitUntil(
            timeoutMillis = 5000
        ) {

            composeTestRule
                .onAllNodesWithText(
                    "Estudar Kotlin"
                )
                .fetchSemanticsNodes()
                .isNotEmpty()
        }


        composeTestRule
            .onNodeWithText("Estudar Kotlin")
            .assertExists()
    }

    @Test
    fun deleteTask_removesTaskFromScreen() {

        composeTestRule
            .onNodeWithText("Nova tarefa")
            .performTextInput("Tarefa para excluir")


        composeTestRule
            .onNodeWithText("Adicionar")
            .performClick()


        composeTestRule.waitUntil(
            timeoutMillis = 5000
        ) {

            composeTestRule
                .onAllNodesWithText(
                    "Tarefa para excluir"
                )
                .fetchSemanticsNodes()
                .isNotEmpty()
        }


        val insertedTask =
            kotlinx.coroutines.runBlocking {

                database
                    .taskDao()
                    .getAllTasks()
                    .first()
                    .first()
            }


        composeTestRule
            .onNodeWithTag(
                "task_delete_${insertedTask.id}"
            )
            .performClick()


        composeTestRule.waitUntil(
            timeoutMillis = 5000
        ) {

            composeTestRule
                .onAllNodesWithText(
                    "Tarefa para excluir"
                )
                .fetchSemanticsNodes()
                .isEmpty()
        }


        composeTestRule
            .onNodeWithText(
                "Tarefa para excluir"
            )
            .assertDoesNotExist()
    }

    @Test
    fun completeTask_updatesTaskAsCompleted() {

        composeTestRule
            .onNodeWithText("Nova tarefa")
            .performTextInput("Estudar Compose")


        composeTestRule
            .onNodeWithText("Adicionar")
            .performClick()

        composeTestRule.waitUntil(
            timeoutMillis = 5000
        ) {

            runBlocking {

                database
                    .taskDao()
                    .getAllTasks()
                    .first()
                    .isNotEmpty()
            }
        }

        val insertedTask =
            runBlocking {

                database
                    .taskDao()
                    .getAllTasks()
                    .first()
                    .first()
            }

        composeTestRule
            .onNodeWithTag(
                "task_checkbox_${insertedTask.id}"
            )
            .performClick()

        composeTestRule.waitUntil(
            timeoutMillis = 5000
        ) {

            runBlocking {

                val task =
                    database
                        .taskDao()
                        .getAllTasks()
                        .first()
                        .find {
                            it.id == insertedTask.id
                        }

                task?.completed == true
            }
        }

        val updatedTask =
            runBlocking {

                database
                    .taskDao()
                    .getAllTasks()
                    .first()
                    .first {
                        it.id == insertedTask.id
                    }
            }


        assertTrue(
            updatedTask.completed
        )
    }

}