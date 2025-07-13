package com.example.xuehanyu.dictionary.data.local.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xuehanyu.dictionary.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)
    
    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)
    
    @Query("DELETE FROM favorites WHERE userId = :userId AND idDictionary = :idDictionary")
    suspend fun removeFavorite(userId: String, idDictionary: Int)
    
    @Query("SELECT * FROM favorites WHERE userId = :userId AND idDictionary = :idDictionary LIMIT 1")
    suspend fun getFavorite(userId: String, idDictionary: Int): FavoriteEntity?
    
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY createdAt DESC")
    fun getFavoritesByUser(userId: String): Flow<List<FavoriteEntity>>
    
    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId AND idDictionary = :idDictionary")
    suspend fun isFavorite(userId: String, idDictionary: Int): Int
    
    @Query("SELECT idDictionary FROM favorites WHERE userId = :userId")
    suspend fun getFavoriteDictionaryIds(userId: String): List<Int>
} 