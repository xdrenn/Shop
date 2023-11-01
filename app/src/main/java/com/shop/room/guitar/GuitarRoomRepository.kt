package com.shop.room.guitar

import com.shop.data.model.Guitar
import kotlinx.coroutines.flow.Flow

interface GuitarRoomRepository {

    fun getGuitars(): Flow<List<Guitar>>

    suspend fun getGuitarById(id: Int): Guitar?

    suspend fun insertGuitar(guitar: Guitar)

    suspend fun deleteGuitar(guitar: Guitar)
}