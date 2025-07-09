package com.example.xuehanyu.auth.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.auth.auth

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private val _email = mutableStateOf("")
    val email: State<String> = _email

    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private val auth: FirebaseAuth by lazy { Firebase.auth }
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
        _loading.value = true
        auth.createUserWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _loading.value = false
                if (task.isSuccessful) {
                    _errorMessage.value = null
                    // User is signed up and logged in
                } else {
                    _errorMessage.value = task.exception?.localizedMessage ?: "Sign up failed."
                }
            }
    }

    fun login() {
        if (_email.value.isBlank() || _password.value.isBlank()) {
            _errorMessage.value = "Email and password must not be empty."
            return
        }
        _loading.value = true
        auth.signInWithEmailAndPassword(_email.value, _password.value)
            .addOnCompleteListener { task ->
                _loading.value = false
                if (task.isSuccessful) {
                    _errorMessage.value = null
                    // User is logged in
                } else {
                    _errorMessage.value = task.exception?.localizedMessage ?: "Login failed."
                }
            }
    }

    fun clearError() {
        _errorMessage.value = null
    }
} 