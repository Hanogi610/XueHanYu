package com.example.xuehanyu.auth.data.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun signOut()
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun skipLogin()
    fun isLoginSkipped(): Flow<Boolean>
    suspend fun getCurrentUserId(): String?
}