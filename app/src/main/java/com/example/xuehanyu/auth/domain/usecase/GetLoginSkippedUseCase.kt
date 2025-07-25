package com.example.xuehanyu.auth.domain.usecase

import com.example.xuehanyu.auth.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoginSkippedUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.isLoginSkipped()
    }
}