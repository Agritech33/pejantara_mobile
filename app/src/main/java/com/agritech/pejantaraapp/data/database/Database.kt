package com.agritech.pejantaraapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Trash::class, LaporanEntity::class], version = 3)
abstract class Database : RoomDatabase() {

    abstract fun trashDao(): TrashDao
    abstract fun laporanDao(): LaporanDao
}
