package com.polina.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    background = backgroundDark,
    error = errorDark,
    surfaceContainer = surfaceContainerDark,
    onSurfaceVariant = onSurfaceVariantDark,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    background = backgroundLight,
    error = errorLight,
    surfaceContainer = surfaceContainerLight,
    onSurfaceVariant = onSurfaceVariantLight
)

private val PinkLightColourScheme = lightColorScheme(
    primary = primaryPinkLight,
    onPrimary = onPrimaryPinkLight,
    primaryContainer = primaryContainerPinkLight,
    background = backgroundPinkLight,
    error = errorPinkLight,
    surfaceContainer = surfaceContainerPinkLight,
    onSurfaceVariant = onSurfaceVariantPinkLight
)
private val PinkDarkColourScheme = darkColorScheme(
    primary = primaryPinkDark,
    onPrimary = onPrimaryPinkDark,
    primaryContainer = primaryContainerPinkDark,
    background = backgroundPinkDark,
    error = errorPinkDark,
    surfaceContainer = surfaceContainerPinkDark,
    onSurfaceVariant = onSurfaceVariantPinkDark
)
private val BlueLightColourScheme = lightColorScheme(
    primary = primaryBlueLight,
    onPrimary = onPrimaryBlueLight,
    primaryContainer = primaryContainerBlueLight,
    background = backgroundBlueLight,
    error = errorBlueLight,
    surfaceContainer = surfaceContainerBlueLight,
    onSurfaceVariant = onSurfaceVariantBlueLight
)

private val BlueDarkColourScheme = darkColorScheme(
    primary = primaryBlueDark,
    onPrimary = onPrimaryBlueDark,
    primaryContainer = primaryContainerBlueDark,
    background = backgroundBlueDark,
    error = errorBlueDark,
    surfaceContainer = surfaceContainerBlueDark,
    onSurfaceVariant = onSurfaceVariantBlueDark
)

@Composable
fun FinTrackrTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    color: String="green",
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        (darkTheme && color=="pink") -> PinkDarkColourScheme
        (!darkTheme && color=="pink") -> PinkLightColourScheme
        (!darkTheme && color=="green") -> LightColorScheme
        (darkTheme && color=="green") -> DarkColorScheme
        (darkTheme && color=="blue") -> BlueDarkColourScheme
        (!darkTheme && color=="blue") -> BlueLightColourScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}