package com.polina.fintrackr.core.ui

data class ListItem(
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: Any? = null,
    val trailingText: String? = null,
    val trailingIcon: Any? = null,
)