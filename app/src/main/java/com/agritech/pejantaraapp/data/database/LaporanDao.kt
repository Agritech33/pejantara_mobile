package com.agritech.pejantaraapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LaporanDao {

    @Insert
    suspend fun insertLaporan(laporan: LaporanEntity)

    @Query("SELECT * FROM laporan_table ORDER BY id DESC")
    fun getAllLaporan(): Flow<List<LaporanEntity>>
}

