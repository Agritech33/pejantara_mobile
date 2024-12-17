package com.agritech.pejantaraapp.ui.screen.history.history_laporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.database.LaporanEntity
import com.agritech.pejantaraapp.data.repository.LaporanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryLaporanViewModel @Inject constructor(
    repository: LaporanRepository
) : ViewModel() {
    val laporanList = repository.getAllLaporan()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}



