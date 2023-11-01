package com.shop.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.data.model.Accessory
import com.shop.data.model.Guitar
import com.shop.room.accessories.AccessoriesRoomRepository
import com.shop.room.guitar.GuitarRoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val guitarRoomRepository: GuitarRoomRepository,
    private val accessoriesRoomRepository: AccessoriesRoomRepository
) : ViewModel() {

    //guitar room methods
    fun addGuitarToCart(guitar: Guitar) = viewModelScope.launch(Dispatchers.IO) {
        guitarRoomRepository.insertGuitar(guitar)
    }

    suspend fun getGuitarCartById(id: Int): Guitar? {
        return guitarRoomRepository.getGuitarById(id)
    }

    fun getGuitarCarts(): Flow<List<Guitar>> {
        return guitarRoomRepository.getGuitars()
    }

    fun deleteGuitarCart(guitar: Guitar) = viewModelScope.launch(Dispatchers.IO) {
        guitarRoomRepository.deleteGuitar(guitar)
    }

    //accessories room methods

    fun addAccessoryToCart(accessory: Accessory) = viewModelScope.launch(Dispatchers.IO) {
        accessoriesRoomRepository.insertAccessory(accessory)
    }

    suspend fun getAccessoryCartById(id: Int): Accessory? {
        return  accessoriesRoomRepository.getAccessoryById(id)
    }

    fun getAccessoriesCart(): Flow<List<Accessory>>{
        return accessoriesRoomRepository.getAccessories()
    }

    fun deleteAccessoryCart(accessory: Accessory) = viewModelScope.launch(Dispatchers.IO){
        accessoriesRoomRepository.deleteAccessory(accessory)
    }
}