package com.agritech.pejantaraapp.ui.screen.detail_result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.repository.TrashRepository
import com.agritech.pejantaraapp.data.repository.TutorialRepository
import com.agritech.pejantaraapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val trashRepository: TrashRepository,
    private val tutorialRepository: TutorialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<Resource<ResultUiState>>(Resource.Loading)
    val uiState: StateFlow<Resource<ResultUiState>> = _uiState.asStateFlow()

    fun loadResult(image: File) {
        viewModelScope.launch {
            trashRepository.fetchResultWithVideos(image).collect { resource ->
                _uiState.value = resource
            }
        }
    }
}









