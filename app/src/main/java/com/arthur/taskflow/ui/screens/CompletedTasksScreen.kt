package com.arthur.taskflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.arthur.taskflow.TaskViewModel

@Composable
fun CompletedTasksScreen(
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {

    val tasks =
        taskViewModel.tasks
            .collectAsState(
                initial = emptyList()
            )
            .value

    val completedTasks =
        tasks.filter { task ->
            task.completed
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tarefas concluídas"
        )

        Button(
            onClick = onBack
        ) {

            Text("Voltar")
        }

        if (completedTasks.isEmpty()) {

            Text(
                text = "Nenhuma tarefa concluída."
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {

                items(completedTasks) { task ->

                    Text(
                        text = task.title,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}