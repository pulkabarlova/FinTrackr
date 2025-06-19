package com.polina.fintrackr.features.articles.ui

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.data.dto.model.category.Category
import com.polina.fintrackr.core.ui.generateMockData
import com.polina.fintrackr.core.ui.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.components.AppScaffold
import com.polina.fintrackr.core.ui.components.AppTopBar
import com.polina.fintrackr.core.ui.components.ListItem
import com.polina.fintrackr.core.ui.components.ListItemUi

@Composable
fun ArticlesScreen(navController: NavController) {
    val mockArticles = remember { getMockArticles() }
    AppScaffold(
        navController = navController,
        content = { paddingValues -> Content(paddingValues = paddingValues, mockArticles) },
        topBar = { AppTopBar( R.string.my_articles) })
}

@Composable
fun CustomSearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(modifier = Modifier.padding(2.dp), text = stringResource(R.string.find_article)
            , style = MaterialTheme.typography.labelLarge
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
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
    )
}

@Composable
fun Content(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    mockArticles: List<Category>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)

    ) {
        CustomSearchBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(items = mockArticles) { article ->
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

fun getMockArticles(): List<Category> {
    val transactions = generateMockData()
    val article = transactions.filter { !it.category.isIncome }.map { it.category }.distinct()
    return article
}

@Preview(name = "Light Mode", showSystemUi = true)
// @Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        ArticlesScreen(navController = NavController(LocalContext.current))
    }
}