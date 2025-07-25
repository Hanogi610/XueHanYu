package com.example.xuehanyu.auth.domain.usecase

import com.example.xuehanyu.auth.data.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(){
        authRepository.signOut()
    }
}