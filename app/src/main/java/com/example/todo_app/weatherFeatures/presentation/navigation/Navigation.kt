package com.example.todo_app.weatherFeatures.presentation.navigation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*

@Composable
fun backHandler(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBack by rememberUpdatedState(onBack)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = LocalBackDispatcher.current
    // If `backDispatcher` changes, dispose and reset the effect
    DisposableEffect(backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
}


internal val LocalBackDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher> {
    error("No Back Dispatcher provided")
}