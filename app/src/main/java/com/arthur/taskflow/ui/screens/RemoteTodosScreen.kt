package com.arthur.taskflow.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.arthur.taskflow.TaskViewModel
import com.arthur.taskflow.model.RemoteTodoUiState

@Composable
fun RemoteTodosScreen(
    taskViewModel: TaskViewModel,
    onBack: () -> Unit
) {

    val state =
        taskViewModel.remoteUiState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Tarefas da API"
        )

        Button(
            onClick = onBack
        ) {

            Text("Voltar")
        }

        Button(
            onClick = {
                taskViewModel.loadRemoteTodos()
            }
        ) {

            Text("Carregar")
        }

        when (state) {

            RemoteTodoUiState.Idle -> {

                Text(
                    "Clique em carregar para buscar os dados."
                )
            }

            RemoteTodoUiState.Loading -> {

                Text(
                    "Carregando..."
                )
            }

            is RemoteTodoUiState.Success -> {

                Text(
                    "Itens carregados: ${state.todos.size}"
                )

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {

                    items(state.todos) { todo ->

                        Row(
                            modifier = Modifier.padding(8.dp)
                        ) {

                            Checkbox(
                                checked = todo.completed,
                                onCheckedChange = null
                            )

                            Text(
                                text = todo.title
                            )
                        }
                    }
                }
            }

            is RemoteTodoUiState.Error -> {

                Text(
                    "Erro: ${state.message}"
                )
            }
        }
    }
}