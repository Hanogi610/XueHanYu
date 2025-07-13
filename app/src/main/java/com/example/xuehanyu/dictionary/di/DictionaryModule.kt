package com.example.xuehanyu.dictionary.di

import android.content.Context
import com.example.xuehanyu.dictionary.data.local.DictionaryDatabase
import com.example.xuehanyu.dictionary.data.local.dao.DictionaryDao
import com.example.xuehanyu.dictionary.data.local.dao.FavoriteDao
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DictionaryModule {

    @Provides
    @Singleton
    fun provideDictionaryDatabase(@ApplicationContext context: Context): DictionaryDatabase {
        return DictionaryDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDictionaryDao(database: DictionaryDatabase): DictionaryDao {
        return database.dictionaryDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: DictionaryDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        dictionaryDao: DictionaryDao,
        favoriteDao: FavoriteDao
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(dictionaryDao, favoriteDao)
    }
} 