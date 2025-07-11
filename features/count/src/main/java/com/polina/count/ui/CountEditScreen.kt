package com.polina.count.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.polina.count.R
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.AccountModel

/**
 * Отвечает за отображение/редактирование UI и обработку взаимодействия пользователя.
 */
@Composable
fun CountEditScreen(
    navController: NavController,
    viewModel: CountEditViewModel = hiltViewModel()
) {
    val error = viewModel.error.collectAsState().value
    val context = LocalContext.current
    val accountState = viewModel.account.collectAsState()
    var updateBalance by remember { mutableStateOf(accountState.value.balance) }
    var selectedCurrency by remember { mutableStateOf(accountState.value.currency) }

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
                accountState.value
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
                        modifier = Modifier.padding(4.dp),
                        text = accountState.balance,
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
                    trailingText = if (selectedCurrency.isNullOrBlank()) accountState.currency else selectedCurrency,
                    trailingIcon = Icons.Default.KeyboardArrowRight,
                ),
                onClick = {
                    showBottomSheet = true
                },
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
