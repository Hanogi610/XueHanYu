package com.example.xuehanyu.auth.domain.usecase

import com.example.xuehanyu.auth.data.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.signUpWithEmailAndPassword(email, password)
    }
} 