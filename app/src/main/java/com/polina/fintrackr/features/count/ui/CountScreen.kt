package com.polina.fintrackr.features.count.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi
import com.polina.fintrackr.features.count.domain.AccountModel
import com.polina.fintrackr.features.count.domain.CountViewModel


@Composable
fun CountScreen(navController: NavController,
                viewModel: CountViewModel = hiltViewModel()){
    AppScaffold(
        navController = navController,
        content = { Content(paddingValues = it, viewModel) },
        topBar = { AppTopBar(R.string.my_count, R.drawable.addit_icon,) })

}
@Composable
fun Content(paddingValues: androidx.compose.foundation.layout.PaddingValues, viewModel: CountViewModel) {
    val accountState = viewModel.account.value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            ListItemUi(ListItem(title = stringResource(R.string.balance),
                leadingIcon = R.drawable.money_icon,
                trailingText = accountState.balance + " " + accountState.currency,
                trailingIcon = Icons.Default.KeyboardArrowRight,),
                onClick = {},
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer))
            ListItemUi(ListItem(title = stringResource(R.string.currency),
                trailingText = accountState.currency,
                trailingIcon = Icons.Default.KeyboardArrowRight,),
                onClick = {},
                modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer))
        }

        FloatingActionButton(
            onClick = {},
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            elevation = FloatingActionButtonDefaults.elevation(0.dp),
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Preview(name = "Light Mode", showSystemUi = true)
// @Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        CountScreen(navController = NavController(LocalContext.current))
    }
}