package com.agritech.pejantaraapp.data.model

data class ProfileState(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val money: Int? = null,
    val photoUri: String? = null,
    val isLocationEnabled: Boolean = false,
    val isLoading: Boolean = true,
    val verificationCode: String? = null,
    val isVerified: Boolean = false
)



