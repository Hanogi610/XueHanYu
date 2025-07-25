package com.example.xuehanyu.auth.domain.usecase

import com.example.xuehanyu.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SkipLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke() {
        authRepository.skipLogin()
    }
}