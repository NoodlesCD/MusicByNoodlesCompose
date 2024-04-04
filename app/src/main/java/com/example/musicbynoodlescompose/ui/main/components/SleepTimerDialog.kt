package com.example.musicbynoodlescompose.ui.main.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.INFINITE
import kotlin.time.Duration.Companion.ZERO
import kotlin.time.Duration.Companion.minutes

@Composable
fun SleepTimerDialog(
    context: Context,
    sleepTimerVal: Duration,
    isSleepDialogOpen: Boolean,
    setIsSleepDialogOpen: (isOpen: Boolean) -> Unit,
    setSleepTimerDuration: (duration: Duration) -> Unit
) {
    val timerValues = listOf(
        Pair("15 minutes", 15.minutes),
        Pair("30 minutes", 30.minutes),
        Pair("45 minutes", 45.minutes),
        Pair("1 hour", 60.minutes),
        Pair("1.5 hours", 90.minutes),
        Pair("2 hours", 120.minutes)
    )

    when {
        isSleepDialogOpen -> {
            AlertDialog(
                title = { Text("Sleep timer") },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (sleepTimerVal != INFINITE) {
                            Text("Time: ${sleepTimerVal.inWholeMinutes} remaining")
                            Divider()
                        }
                        for (value in timerValues) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = value.first,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            setSleepTimerDuration(value.second)
                                            setIsSleepDialogOpen(false)
                                            Toast.makeText(context, "Timer set for ${value.first}", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                            )
                        }
                        if (sleepTimerVal != INFINITE) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Cancel timer",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            setSleepTimerDuration(Duration.INFINITE)
                                            setIsSleepDialogOpen(false)
                                            Toast.makeText(context, "Sleep timer cancelled", Toast.LENGTH_SHORT).show()
                                        }
                                    )
                            )
                        }
                    }
                },
                onDismissRequest = { setIsSleepDialogOpen(false) },
                confirmButton = {
                    TextButton(
                        onClick = { setIsSleepDialogOpen(false) }
                    ) {
                        Text(
                            text = "Dismiss",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                containerColor = MaterialTheme.colorScheme.background
            )
        }
    }
}