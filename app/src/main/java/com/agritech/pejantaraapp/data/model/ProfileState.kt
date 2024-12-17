package com.agritech.pejantaraapp.data.model

data class ProfileState(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val photoUri: String? = null,
    val money: Int? = null,
    val isNotificationEnabled: Boolean = false,
    val isLocationEnabled: Boolean = false
)


