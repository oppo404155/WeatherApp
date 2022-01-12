package com.example.todo_app.weatherFeatures.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableSectionHeader(
    title: String,
    subtitle: String,
    expanded: Boolean,
    onToggleState: () -> Unit
) {
    val rotation: Float by animateFloatAsState(if (expanded) 180f else 0f)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleState() }
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
            )
            Spacer(modifier = Modifier.height(2.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
        }
        Icon(
            imageVector = Icons.Rounded.ArrowDropDown,
            contentDescription = stringResource(com.example.todo_app.R.string.expandable_header_toggle_message),
            modifier = Modifier
                .size(28.dp)
                .rotate(rotation)
        )
    }
}