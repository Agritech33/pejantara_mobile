package com.agritech.pejantaraapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laporan_table")
data class LaporanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jenis: String,
    val deskripsi: String,
    val berat: Int,
    val tanggal: String,
    val isNew: Boolean = true
)

