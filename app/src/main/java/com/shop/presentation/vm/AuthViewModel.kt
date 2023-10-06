package com.shop.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shop.data.repository.AuthRepository
import com.shop.state.AuthState
import com.shop.utils.AuthResult
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
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState(isLoading = false))
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val resultChannel = Channel<AuthResult<Unit>>()
    var authResult = resultChannel.receiveAsFlow()


    init {
        authenticate()
    }

    fun signUp(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.signUp(
                username = username,
                password = password
            )
            resultChannel.send(result)
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _state.update { currentState ->
                currentState.copy(isLoading = true)
            }
            val result = repository.login(username = username, password = password)
            resultChannel.send(result)
            _state.update { currentState ->
                currentState.copy(isLoading = false, isAuthSuccessful = true)
            }
        } catch (e: IOException) {
            _state.update { currentState ->
                currentState.copy(errorMessage = "Something went wrong")
            }
        }
    }

    private fun authenticate() = viewModelScope.launch(Dispatchers.IO) {
        val result = repository.authenticate()
        resultChannel.send(result)
    }
}