package com.shop.room.accessories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shop.data.model.Accessory
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessoriesDao {

    @Query("SELECT * FROM accessories_table")
    fun getAccessories(): Flow<List<Accessory>>

    @Query("SELECT * FROM accessories_table WHERE id = :id")
    suspend fun getAccessoryById(id: Int): Accessory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccessory(accessory: Accessory)

    @Delete
    suspend fun deleteAccessory(accessory: Accessory)
}