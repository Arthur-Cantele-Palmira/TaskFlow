package com.arthur.taskflow.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.arthur.taskflow.TaskViewModel
import com.arthur.taskflow.TaskViewModelFactory
import com.arthur.taskflow.data.TaskRepository
import com.arthur.taskflow.data.local.TaskDatabase
import com.arthur.taskflow.ui.screens.CompletedTasksScreen
import com.arthur.taskflow.ui.screens.RemoteTodosScreen
import com.arthur.taskflow.ui.screens.TaskScreen

@Composable
fun TaskFlowApp() {

    val context =
        LocalContext.current

    val database =
        remember {
            TaskDatabase.getDatabase(context)
        }

    val repository =
        remember {
            TaskRepository(
                taskDao = database.taskDao()
            )
        }

    val factory =
        remember {
            TaskViewModelFactory(repository)
        }

    val taskViewModel: TaskViewModel =
        viewModel(
            factory = factory
        )

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "tasks"
    ) {

        composable("tasks") {

            TaskScreen(
                taskViewModel = taskViewModel,

                onOpenCompleted = {
                    navController.navigate("completed")
                },

                onOpenRemote = {
                    navController.navigate("remote")
                }
            )
        }

        composable("completed") {

            CompletedTasksScreen(
                taskViewModel = taskViewModel,

                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("remote") {

            RemoteTodosScreen(
                taskViewModel = taskViewModel,

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}