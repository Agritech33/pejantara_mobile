package com.agritech.pejantaraapp.ui.screen.detail_result

import com.agritech.pejantaraapp.data.model.TutorialModel

data class ResultUiState(
    val trashType: String,
    val imageUrl: String,
    val videos: List<TutorialModel>
)
