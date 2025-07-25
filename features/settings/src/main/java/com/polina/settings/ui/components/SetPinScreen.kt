package com.polina.settings.ui.components

import Vibrate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.polina.data.db.CryptoPref
import com.polina.settings.R
import provideVibration

@Composable
fun SetPinScreen(
    onPinSet: () -> Unit,
    cryptoPref: CryptoPref,
    fromScreen: String,
    sound: String = "true"
) {

    val savedPin = cryptoPref.getString("password")
    val isPinAlreadySet = savedPin.isNotEmpty()
    val isFromSettings = fromScreen == "settings"
    val context = LocalContext.current

    var step by remember { mutableStateOf(1) }
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    val err1 = stringResource(R.string.incorrect_pin)
    val err2 = stringResource(R.string.pin_mismatch)

    val entered = if (!isFromSettings && isPinAlreadySet || step == 1) pin else confirmPin

    val onDigitClick: (String) -> Unit = {
        provideVibration(context, isVibrationEnabled = sound)
        if (entered.length < 4) {
            if (!isFromSettings && isPinAlreadySet || step == 1) {
                pin += it
            } else {
                confirmPin += it
            }
        }
    }

    val onDelete = {
        provideVibration(context, isVibrationEnabled = sound)
        if ((!isFromSettings && isPinAlreadySet || step == 1) && pin.isNotEmpty()) {
            pin = pin.dropLast(1)
        } else if ((isFromSettings && step == 2) && confirmPin.isNotEmpty()) {
            confirmPin = confirmPin.dropLast(1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = when {
                !isFromSettings && isPinAlreadySet -> stringResource(R.string.login)
                step == 1 -> stringResource(R.string.new_pin)
                else -> stringResource(R.string.repeat_pin)
            },
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            repeat(4) { index ->
                val filled = entered.length > index
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = if (filled) MaterialTheme.colorScheme.primary else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        NumericKeypad(onDigitClick = onDigitClick, onDelete = onDelete)

        Spacer(modifier = Modifier.height(16.dp))

        error?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        if (entered.length == 4) {
            Button(onClick = {
                if (!isFromSettings && isPinAlreadySet) {
                    if (pin == savedPin) {
                        onPinSet()
                    } else {
                        error = err1
                        pin = ""
                    }
                } else {
                    if (step == 1) {
                        step = 2
                    } else {
                        if (pin == confirmPin) {
                            cryptoPref.putString("password", pin)
                            onPinSet()
                        } else {
                            error = err2
                            confirmPin = ""
                            pin = ""
                            step = 1
                        }
                    }
                }
            }) {
                Text(
                    when {
                        !isFromSettings && isPinAlreadySet -> stringResource(R.string.login)
                        step == 1 -> stringResource(R.string.continu)
                        else -> stringResource(R.string.save)
                    }
                )
            }
        }
    }
}
