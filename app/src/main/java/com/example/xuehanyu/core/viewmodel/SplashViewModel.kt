package com.example.xuehanyu.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _isSplashVisible = MutableStateFlow(true)
    val isSplashVisible: StateFlow<Boolean> = _isSplashVisible.asStateFlow()

    private val _splashProgress = MutableStateFlow(0f)
    val splashProgress: StateFlow<Float> = _splashProgress.asStateFlow()

    companion object {
        private const val SPLASH_DURATION = 2000L // 2 seconds
        private const val PROGRESS_UPDATE_INTERVAL = 50L // Update progress every 50ms
    }

    init {
        startSplashTimer()
    }

    private fun startSplashTimer() {
        viewModelScope.launch {
            val totalSteps = (SPLASH_DURATION / PROGRESS_UPDATE_INTERVAL).toInt()
            
            repeat(totalSteps) { step ->
                delay(PROGRESS_UPDATE_INTERVAL)
                _splashProgress.value = (step + 1).toFloat() / totalSteps
            }
            
            _isSplashVisible.value = false
        }
    }

    /**
     * Manually hide the splash screen (useful for testing or emergency cases)
     */
    fun hideSplash() {
        _isSplashVisible.value = false
        _splashProgress.value = 1f
    }

    /**
     * Reset splash screen (useful for testing)
     */
    fun resetSplash() {
        _isSplashVisible.value = true
        _splashProgress.value = 0f
        startSplashTimer()
    }
} 
 