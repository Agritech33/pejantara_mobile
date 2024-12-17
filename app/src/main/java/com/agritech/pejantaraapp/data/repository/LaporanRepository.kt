package com.agritech.pejantaraapp.data.repository

import com.agritech.pejantaraapp.data.database.LaporanDao
import com.agritech.pejantaraapp.data.database.LaporanEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaporanRepository @Inject constructor(private val laporanDao: LaporanDao) {

    fun getAllLaporan(): Flow<List<LaporanEntity>> = laporanDao.getAllLaporan()

    suspend fun insertLaporan(laporan: LaporanEntity) {
        laporanDao.insertLaporan(laporan)
    }
}

