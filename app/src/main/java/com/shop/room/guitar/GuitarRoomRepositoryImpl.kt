package com.shop.room.guitar

import com.shop.data.model.Guitar
import kotlinx.coroutines.flow.Flow

class GuitarRoomRepositoryImpl(
    private val guitarDao: GuitarDao
) : GuitarRoomRepository {
    override fun getGuitars(): Flow<List<Guitar>> {
        return guitarDao.getGuitars()
    }

    override suspend fun getGuitarById(id: Int): Guitar? {
        return guitarDao.getGuitarById(id)
    }

    override suspend fun insertGuitar(guitar: Guitar) {
        return guitarDao.insertGuitar(guitar)
    }

    override suspend fun deleteGuitar(guitar: Guitar) {
        return guitarDao.deleteGuitar(guitar)
    }
}