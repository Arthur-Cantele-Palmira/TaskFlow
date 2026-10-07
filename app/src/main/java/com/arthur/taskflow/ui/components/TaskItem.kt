package com.arthur.taskflow.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.arthur.taskflow.model.Task

@Composable
fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {

    Row(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(
            text = task.title
        )

        Checkbox(
            checked = task.completed,

            onCheckedChange = { novoValor ->
                onCheckedChange(novoValor)
            },

            modifier = Modifier.testTag(
                "task_checkbox_${task.id}"
            )
        )

        Button(
            onClick = {
                onDelete()
            },

            modifier = Modifier.testTag(
                "task_delete_${task.id}"
            )
        ) {

            Text("Excluir")
        }
    }
}