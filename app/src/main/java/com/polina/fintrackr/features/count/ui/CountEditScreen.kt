package com.polina.fintrackr.features.count.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.data.dto.request.AccountCreateRequest
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi
import com.polina.fintrackr.features.count.domain.AccountModel

/**
 * Отвечает за отображение/редактирование UI и обработку взаимодействия пользователя.
 */
@Composable
fun CountEditScreen(
    navController: NavController,
    viewModel: CountEditViewModel = hiltViewModel()
) {
    val error = viewModel.error.value
    val context = LocalContext.current
    val accountState = viewModel.account.value
    var updateBalance by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf(accountState.currency) }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    AppScaffold(
        navController = navController,
        content = {
            ContentEdit(
                paddingValues = it,
                updateBalance = updateBalance,
                onUpdateBalance = { updateBalance = it },
                selectedCurrency = selectedCurrency,
                onSelectedCurrency = { selectedCurrency = it },
                accountState
            )
        },
        topBar = {
            AppTopBar(
                R.string.edit_screen,
                isLeading = Icons.Default.Close,
                isTrailing = Icons.Default.Check,
                onBackIconClick = { navController.navigate("count") },
                onTrailingIconClick = {
                    viewModel.setBalance(updateBalance)
                    viewModel.setCurrency(selectedCurrency)
                    viewModel.updateAccount()
                    navController.navigate("count")
                }
            )
        })
}

@Composable
fun ContentEdit(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    updateBalance: String,
    onUpdateBalance: (String) -> Unit,
    selectedCurrency: String,
    onSelectedCurrency: (String) -> Unit,
    accountState: AccountModel
) {

    var showBottomSheet by remember { mutableStateOf(false) }
    LaunchedEffect(accountState) {}
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
            TextField(
                value = updateBalance,
                onValueChange = { newValue ->
                    var dotFound = false
                    val filtered = newValue.filter { char ->
                        when {
                            char.isDigit() -> true
                            char == '.' && !dotFound -> {
                                dotFound = true
                                true
                            }

                            else -> false
                        }
                    }
                    onUpdateBalance(filtered)
                },
                placeholder = {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = stringResource(R.string.balance),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),

                )
            ListItemUi(
                ListItem(
                    title = stringResource(R.string.currency),
                    trailingText = selectedCurrency,
                    trailingIcon = Icons.Default.KeyboardArrowRight,
                ),
                onClick = {
                    showBottomSheet = true
                },
            )
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
        if (showBottomSheet) {
            CurrencyBottomSheet(
                onSelectCurrency = { currency ->
                    onSelectedCurrency(currency)
                    showBottomSheet = false
                },
                onCancel = {
                    showBottomSheet = false
                }
            )
        }
    }
}
