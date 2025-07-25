package com.example.xuehanyu.main.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xuehanyu.auth.domain.usecase.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            try {
                signOutUseCase()
            } catch (e: Exception) {
                // Handle sign out error if needed
                e.printStackTrace()
            }
        }
    }
}