package com.agritech.pejantaraapp.ui.screen.deteksi_sampah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.repository.TrashRepository
import com.agritech.pejantaraapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val trashRepository: TrashRepository
) : ViewModel() {

    private val _uploadState = MutableStateFlow<Resource<Long>>(Resource.Loading)
    val uploadState: StateFlow<Resource<Long>> = _uploadState.asStateFlow()

    fun uploadImage(imageFile: File) {
        viewModelScope.launch {
            _uploadState.value = Resource.Loading
            trashRepository.predictTrash(imageFile).collect { resource ->
                _uploadState.value = resource
            }
        }
    }

    fun resetUploadState() {
        _uploadState.value = Resource.Loading
    }
}




