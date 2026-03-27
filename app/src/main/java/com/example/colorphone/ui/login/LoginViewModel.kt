package com.example.colorphone.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorphone.domain.model.User
import com.example.colorphone.domain.repository.CinemaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.colorphone.util.SessionManager

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val cinemaRepository: CinemaRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _loginSuccess = MutableSharedFlow<User>()
    val loginSuccess: SharedFlow<User> = _loginSuccess

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val user = cinemaRepository.login(email, passwordHash)
            if (user != null) {
                sessionManager.saveUserId(user.id)
                _loginState.value = LoginState.Success(user)
                _loginSuccess.emit(user)
            } else {
                _loginState.value = LoginState.Error("Invalid email or password")
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: User) : LoginState()
        data class Error(val message: String) : LoginState()
    }
}
