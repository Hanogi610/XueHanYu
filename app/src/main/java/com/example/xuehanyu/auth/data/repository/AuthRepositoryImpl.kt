package com.example.xuehanyu.auth.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.xuehanyu.core.util.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_preferences")

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val context: Context,
    private val sharedPrefHelper: SharedPrefHelper
) : AuthRepository {

    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")
    private val isLoginSkippedKey = booleanPreferencesKey("is_login_skipped")

    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                saveLoginState(true, result.user!!.uid)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Login failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                saveLoginState(true, result.user!!.uid)
                Result.success(Unit)
            } else {
                Result.failure(Exception("Sign up failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
        saveLoginState(false, null)
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[isLoggedInKey] ?: false
        }
    }

    override suspend fun skipLogin() {
        val isLoginSkipped = sharedPrefHelper.getLoginSkipped()
        saveSkipLoginState(!isLoginSkipped)
    }

    override fun isLoginSkipped(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[isLoginSkippedKey] ?: false
        }
    }

    private suspend fun saveSkipLoginState(isSkipped: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[isLoginSkippedKey] = isSkipped
        }
    }

    override suspend fun getCurrentUserId(): String? {
        val userId = sharedPrefHelper.getUserId()
        return if (userId != "default_user") userId else null
    }

    private suspend fun saveLoginState(isLoggedIn: Boolean, userId: String?) {
        context.dataStore.edit { preferences ->
            preferences[isLoggedInKey] = isLoggedIn
        }
        
        if (userId != null) {
            sharedPrefHelper.setUserId(userId)
        } else {
            sharedPrefHelper.setUserId("default_user")
        }
    }
}