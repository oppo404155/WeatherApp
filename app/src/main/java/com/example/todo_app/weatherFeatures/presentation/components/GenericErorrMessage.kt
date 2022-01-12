package com.example.todo_app.weatherFeatures.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.todo_app.R

@Composable
fun ErrorMessage(message: String = stringResource(R.string.generic_error_message)) {
    Text(
        text = message,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    )
}