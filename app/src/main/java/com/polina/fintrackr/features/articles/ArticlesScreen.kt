package com.polina.fintrackr.features.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.polina.fintrackr.R
import com.polina.fintrackr.core.theme.FinTrackrTheme
import com.polina.fintrackr.core.ui.AppScaffold

@Composable
fun ArticlesScreen(navController: NavController) {
    AppScaffold(
        navController = navController,
        content = { paddingValues -> Content(paddingValues = paddingValues) },
        topBar = { TopBar() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = {
            Text(
                text = stringResource(id = R.string.my_articles),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}

@Composable
fun CustomSearchBar() {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(modifier = Modifier.padding(4.dp), text = stringResource(R.string.find_article))
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
            )
        },
        modifier = Modifier
            .fillMaxWidth(),
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
fun Content(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
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
            repeat(9) {
                item { ArticleItem("Текст") }
            }
        }
    }
}

@Composable
fun ArticleItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color.Gray,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.home_icon),
            contentDescription = "article_icon",
            tint = Color.Unspecified
        )
        Text(
            text = title,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp, vertical = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(name = "Light Mode", showSystemUi = true)
// @Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun Preview() {
    FinTrackrTheme {
        ArticlesScreen(navController = NavController(LocalContext.current))
    }
}