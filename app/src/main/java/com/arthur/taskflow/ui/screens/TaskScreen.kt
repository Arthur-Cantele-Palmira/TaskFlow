package com.arthur.taskflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.arthur.taskflow.TaskViewModel
import com.arthur.taskflow.ui.components.TaskItem

@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel,
    onOpenCompleted: () -> Unit,
    onOpenRemote: () -> Unit
) {

    val taskName = remember {
        mutableStateOf("")
    }

    val tasks =
        taskViewModel.tasks
            .collectAsState(
                initial = emptyList()
            )
            .value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("TaskFlow")

        Text("Minhas tarefas")

        TextField(
            value = taskName.value,

            onValueChange = { novoValor ->
                taskName.value = novoValor
            },

            label = {
                Text("Nova tarefa")
            }
        )

        Button(
            onClick = {

                taskViewModel.addTask(
                    taskName.value
                )

                taskName.value = ""
            }
        ) {

            Text("Adicionar")
        }

        Button(
            onClick = onOpenCompleted
        ) {

            Text("Ver concluídas")
        }

        Button(
            onClick = onOpenRemote
        ) {

            Text("Tarefas da API")
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(tasks) { task ->

                TaskItem(
                    task = task,

                    onCheckedChange = { novoValor ->

                        taskViewModel.updateTaskCompleted(
                            taskId = task.id,
                            completed = novoValor
                        )
                    },

                    onDelete = {

                        taskViewModel.deleteTask(
                            task.id
                        )
                    }
                )
            }
        }
    }
}