package com.polina.fintrackr.core.ui.components

data class ListItem(
    val title: String,
    val subtitle: String? = null,
    val leadingIcon: Any? = null,
    val trailingText: String? = null,
    val trailingBottomText: String? = null,
    val trailingIcon: Any? = null,
)