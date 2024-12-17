package com.agritech.pejantaraapp.ui.screen.history.history_scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.agritech.pejantaraapp.data.database.Trash
import com.agritech.pejantaraapp.data.repository.TrashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryScanViewModel @Inject constructor(
    private val trashRepository: TrashRepository
) : ViewModel() {
    val trashHistory: LiveData<List<Trash>> = trashRepository.getTrashHistory()
}

