package com.polina.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.data.db.CryptoPref
import com.polina.splash.R
import com.polina.ui.navigation.daggerViewModel
import kotlinx.coroutines.delay

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */
@Composable
fun SplashScreen(
    navController: NavController,
    cryptoPref: CryptoPref,
    viewModel: SplashViewModel = daggerViewModel()
) {
    val alpha = remember { Animatable(0f) }
    val initialized = viewModel.accountInitialized.value
    val errorMessage = viewModel.errorMessage.value

    var route = "expenses"
    val savedPin = cryptoPref.getString("password")
    if (savedPin.isNotEmpty()) {
        route = "pin"
    }
    LaunchedEffect(initialized) {
        if (initialized != null) {
            alpha.animateTo(1f, animationSpec = tween(1000))
            delay(500)
            navController.navigate(route) {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .alpha(alpha.value)
                    .scale(alpha.value)
            )
            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.background,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}