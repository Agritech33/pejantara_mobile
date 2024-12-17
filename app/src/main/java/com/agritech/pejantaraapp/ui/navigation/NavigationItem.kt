package com.agritech.pejantaraapp.ui.navigation

import androidx.annotation.DrawableRes

data class NavigationItem(
    val title: String,
    @DrawableRes
    val iconResId: Int,
    val screen: Screen
)