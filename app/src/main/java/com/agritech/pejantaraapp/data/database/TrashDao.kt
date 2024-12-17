package com.agritech.pejantaraapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrashDao {

    @Insert
    suspend fun insert(trash: Trash): Long

    @Query("SELECT * FROM user_trash ORDER BY id DESC")
    fun getAll(): LiveData<List<Trash>>

    @Query("SELECT * FROM user_trash ORDER BY id DESC")
    suspend fun getAllSync(): List<Trash>

    @Query("SELECT * FROM user_trash WHERE id = :id")
    fun getTrashById(id: Long): LiveData<Trash>

//    @Delete
//    suspend fun deleteTrash(trash: Trash)

    @Delete
    suspend fun deleteTrashList(trashList: List<Trash>)
}

