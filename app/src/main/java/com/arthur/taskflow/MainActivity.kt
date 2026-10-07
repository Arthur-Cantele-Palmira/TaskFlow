package com.arthur.taskflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

import com.arthur.taskflow.ui.TaskFlowApp
import com.arthur.taskflow.ui.theme.TaskFlowTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        setContent {

            TaskFlowTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =
                        MaterialTheme
                            .colorScheme
                            .background
                ) {

                    TaskFlowApp()
                }
            }
        }
    }
}