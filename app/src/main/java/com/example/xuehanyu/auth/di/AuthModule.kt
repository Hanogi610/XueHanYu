package com.example.xuehanyu.auth.di

import android.content.Context
import com.example.xuehanyu.auth.data.repository.AuthRepository
import com.example.xuehanyu.auth.data.repository.AuthRepositoryImpl
import com.example.xuehanyu.core.util.SharedPrefHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        @ApplicationContext context: Context,
        sharedPrefHelper: SharedPrefHelper
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, context, sharedPrefHelper)
    }
} 