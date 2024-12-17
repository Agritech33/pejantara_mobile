package com.agritech.pejantaraapp.ui.screen.lapor

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agritech.pejantaraapp.data.database.LaporanEntity
import com.agritech.pejantaraapp.data.repository.LaporanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaporViewModel @Inject constructor(
    private val laporanRepository: LaporanRepository
) : ViewModel() {

    fun tambahLaporan(
        jenis: String,
        deskripsi: String,
        berat: Int,
        tanggal: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        updateCoins: (Int) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Buat laporan baru
                val laporan = LaporanEntity(
                    jenis = jenis,
                    deskripsi = deskripsi,
                    berat = berat,
                    tanggal = tanggal
                )
                laporanRepository.insertLaporan(laporan)

                // Hitung koin berdasarkan berat sampah
                val coinsEarned = berat * 1000 // Contoh: 1 kg = 1000 koin
                updateCoins(coinsEarned) // Beritahu ViewModel Profil untuk memperbarui koin

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.localizedMessage ?: "Gagal menambahkan laporan.")
            }
        }
    }
}


