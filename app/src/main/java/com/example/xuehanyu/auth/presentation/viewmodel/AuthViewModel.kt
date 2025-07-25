package com.example.xuehanyu.auth.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xuehanyu.auth.domain.usecase.GetLoginSkippedUseCase
import com.example.xuehanyu.auth.domain.usecase.SkipLoginUseCase
import com.example.xuehanyu.auth.domain.usecase.GetLoginStateUseCase
import com.example.xuehanyu.auth.domain.usecase.SignInUseCase
import com.example.xuehanyu.auth.domain.usecase.SignUpUseCase
import com.example.xuehanyu.core.util.SharedPrefHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val getLoginStateUseCase: GetLoginStateUseCase,
    private val skipLoginUseCase: SkipLoginUseCase,
    private val getLoginSkippedUseCase: GetLoginSkippedUseCase
) : ViewModel() {
    
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _isInitializing = MutableStateFlow(true)
    val isInitializing: StateFlow<Boolean> = _isInitializing.asStateFlow()

    private val _isLoginSkipped = MutableStateFlow(false)
    val isLoginSkipped: StateFlow<Boolean> = _isLoginSkipped.asStateFlow()

    init {
        observeLoginState()
        observeLoginSkip()
    }

    private fun observeLoginState() {
        viewModelScope.launch {
            getLoginStateUseCase().collect { isLoggedIn ->
                _isLoggedIn.value = isLoggedIn
                _isInitializing.value = false
            }
        }
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun signUp() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Email and password must not be empty."
            return
        }
        
        viewModelScope.launch {
        _loading.value = true
            _errorMessage.value = null
            
            val result = signUpUseCase(_email.value, _password.value)
            
            if (result.isSuccess) {
                    _errorMessage.value = null
                } else {
                _errorMessage.value = result.exceptionOrNull()?.localizedMessage ?: "Sign up failed."
                }
            
            _loading.value = false
            }
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Email and password must not be empty."
            return
        }
        
        viewModelScope.launch {
        _loading.value = true
            _errorMessage.value = null
            
            val result = signInUseCase(_email.value, _password.value)
            
            if (result.isSuccess) {
                    _errorMessage.value = null
                } else {
                _errorMessage.value = result.exceptionOrNull()?.localizedMessage ?: "Login failed."
                }
            
            _loading.value = false
            }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun observeLoginSkip(){
        viewModelScope.launch {
            getLoginSkippedUseCase().collect { isSkipped ->
                _isLoginSkipped.value = isSkipped
            }
        }
    }

    fun skipLogin() {
        viewModelScope.launch {
            skipLoginUseCase()
        }
    }
}