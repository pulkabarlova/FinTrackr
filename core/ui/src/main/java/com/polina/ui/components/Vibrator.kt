import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

fun provideVibration(
    context: Context,
    isDelete: Boolean = false,
    isVibrationEnabled: String="true"
) {
    if (isVibrationEnabled != "true") return

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    if (!vibrator.hasVibrator()) return

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = if (isDelete) {
            VibrationEffect.createWaveform(longArrayOf(0, 40, 30, 60), -1)
        } else {
            VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE)
        }
        vibrator.vibrate(effect)
    } else {
        if (isDelete) {
            vibrator.vibrate(longArrayOf(0, 40, 30, 60), -1)
        } else {
            vibrator.vibrate(20)
        }
    }
}

@Composable
fun Vibrate(context: Context,isVibrationEnabled: String) {
    LaunchedEffect(Unit) {
        provideVibration(
            context = context, isVibrationEnabled=isVibrationEnabled
        )
    }
}