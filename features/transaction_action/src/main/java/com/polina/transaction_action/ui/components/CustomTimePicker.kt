package com.polina.transaction_action.ui.components


import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun CustomTimePicker(
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    DisposableEffect(Unit) {
        val dialog = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                onTimeSelected(selectedHour, selectedMinute)
                onDismiss()
            },
            hour,
            minute,
            true
        )
        dialog.setOnCancelListener { onDismiss() }
        dialog.show()

        onDispose { dialog.dismiss() }
    }
}
