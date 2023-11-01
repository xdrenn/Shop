package com.shop.room.guitar

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shop.data.model.Guitar
import kotlinx.coroutines.flow.Flow

@Dao
interface GuitarDao {

    @Query("SELECT * FROM guitar_table")
    fun getGuitars(): Flow<List<Guitar>>

    @Query("SELECT * FROM guitar_table WHERE id = :id")
    suspend fun getGuitarById(id: Int): Guitar?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuitar(guitar: Guitar)

    @Delete
    suspend fun deleteGuitar(guitar: Guitar)
}