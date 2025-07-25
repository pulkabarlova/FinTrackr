package com.polina.articles.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.articles.R
import com.polina.ui.components.AppScaffold
import com.polina.ui.components.AppTopBar
import com.polina.ui.components.ListItem
import com.polina.ui.components.ListItemUi
import com.polina.ui.models.CategoryModel
import com.polina.ui.navigation.daggerViewModel

/**
 * Отвечает за отображение UI и обработку взаимодействия пользователя.
 */

@Composable
fun ArticlesScreen(
    navController: NavController,
    viewModel: ArticlesViewModel = daggerViewModel()
) {
    val categories = viewModel.categories.value
    val error = viewModel.error.value
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    val filteredItems = remember(searchQuery, categories) {
        if (searchQuery.isBlank()) {
            categories
        } else {
            categories.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }
    AppScaffold(
        navController = navController,
        content = { paddingValues ->
            Content(paddingValues = paddingValues,
                searchQuery = searchQuery,
                filteredItems = filteredItems,
                onSearchQueryChange = { searchQuery = it })
        },
        topBar = { AppTopBar(R.string.my_articles) })
}

@Composable
fun CustomSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    TextField(
        value = searchQuery,
        onValueChange = {onSearchQueryChange(it)},
        placeholder = {
            Text(
                modifier = Modifier.padding(2.dp),
                text = stringResource(R.string.find_article),
                style = MaterialTheme.typography.labelLarge
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, MaterialTheme.colorScheme.surfaceContainer),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        )
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    filteredItems: List<CategoryModel>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)

    ) {
        CustomSearchBar(searchQuery, onSearchQueryChange)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(items = filteredItems) { article ->
                ListItemUi(
                    ListItem(
                        title = article.name,
                        leadingIcon = article.emoji
                    ),
                )
            }
        }
    }
}
