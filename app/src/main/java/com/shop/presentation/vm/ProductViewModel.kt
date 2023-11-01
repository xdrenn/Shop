package com.shop.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.data.model.Accessory
import com.shop.data.model.Guitar
import com.shop.data.repository.AccessoryRepository
import com.shop.data.repository.GuitarRepository
import com.shop.state.ProductState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val guitarRepository: GuitarRepository,
    private val accessoriesRepository: AccessoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductState(isLoading = false))
    val state: StateFlow<ProductState> = _state.asStateFlow()

    private val guitarChannel = Channel<List<Guitar>>()
    var guitarResult = guitarChannel.receiveAsFlow()

    private val accessoryChannel = Channel<List<Accessory>>()
    var accessoryResult = accessoryChannel.receiveAsFlow()

    //load guitars
    fun loadGuitarByCategory(category: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = guitarRepository.getGuitarByCategory(category).body()?.guitar
            if (result != null) {
                guitarChannel.send(result)
            }

            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    fun loadGuitarByBrand(brand: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = guitarRepository.getGuitarByBrand(brand).body()?.guitar
            if (result != null) {
                guitarChannel.send(result)
            }
            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    fun loadGuitarById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = guitarRepository.getGuitarById(id).body()?.guitar
            if (result != null) {
                guitarChannel.send(result)
            }

            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    //load accessories
    fun loadAccessoryByCategory(category: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = accessoriesRepository.getAccessoryByCategory(category).body()?.accessory
            if (result != null) {
                accessoryChannel.send(result)
            }

            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    fun loadAccessoryById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = accessoriesRepository.getAccessoryById(id).body()?.accessory
            if (result != null) {
                accessoryChannel.send(result)
            }

            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }

    fun loadAccessoryBySubcategory(subcategory: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = accessoriesRepository.getAccessoryBySubcategory(subcategory).body()?.accessory
            if (result != null) {
                accessoryChannel.send(result)
            }

            _state.update { currentState ->
                currentState.copy(isLoading = false, isSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(isError = true)
            }
        }
    }
}





