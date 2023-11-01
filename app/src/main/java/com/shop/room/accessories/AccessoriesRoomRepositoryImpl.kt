package com.shop.room.accessories

import com.shop.data.model.Accessory
import kotlinx.coroutines.flow.Flow

class AccessoriesRoomRepositoryImpl(
    private val accessoriesDao: AccessoriesDao
) : AccessoriesRoomRepository {
    override fun getAccessories(): Flow<List<Accessory>> {
        return accessoriesDao.getAccessories()
    }

    override suspend fun getAccessoryById(id: Int): Accessory? {
        return accessoriesDao.getAccessoryById(id)
    }

    override suspend fun insertAccessory(accessory: Accessory) {
        return accessoriesDao.insertAccessory(accessory)
    }

    override suspend fun deleteAccessory(accessory: Accessory) {
        return accessoriesDao.deleteAccessory(accessory)
    }
}