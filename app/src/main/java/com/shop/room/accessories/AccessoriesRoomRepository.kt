package com.shop.room.accessories

import com.shop.data.model.Accessory
import kotlinx.coroutines.flow.Flow

interface AccessoriesRoomRepository {

    fun getAccessories(): Flow<List<Accessory>>

    suspend fun getAccessoryById(id: Int): Accessory?

    suspend fun insertAccessory(accessory: Accessory)

    suspend fun deleteAccessory(accessory: Accessory)
}