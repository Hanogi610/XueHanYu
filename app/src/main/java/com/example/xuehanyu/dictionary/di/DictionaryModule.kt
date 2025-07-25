package com.example.xuehanyu.dictionary.di

import android.content.Context
import com.example.xuehanyu.core.database.AppDatabase
import com.example.xuehanyu.dictionary.data.local.dao.DictionaryDao
import com.example.xuehanyu.dictionary.data.local.dao.FavoriteDao
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepositoryImpl
import com.example.xuehanyu.reader.data.dao.BookDao
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
    fun provideDictionaryDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideDictionaryDao(database: AppDatabase): DictionaryDao {
        return database.dictionaryDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
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