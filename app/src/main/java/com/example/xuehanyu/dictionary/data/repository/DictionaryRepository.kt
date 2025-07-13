package com.example.xuehanyu.dictionary.data.repository

import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite
import com.example.xuehanyu.dictionary.data.model.Difficulty
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {
    suspend fun searchCharacter(character: String): DictionaryEntry?
    suspend fun searchByPinyin(pinyin: String): List<DictionaryEntry>
    suspend fun searchByMeaning(meaning: String): List<DictionaryEntry>
    suspend fun getRandomCharacters(count: Int): List<DictionaryEntry>
    suspend fun getCharactersByDifficulty(difficulty: Difficulty): List<DictionaryEntry>
    suspend fun searchAll(query: String): List<DictionaryEntry>
    suspend fun searchAllWithFavorites(query: String, userId: String): List<DictionaryEntryWithFavorite>
    suspend fun addToFavorites(userId: String, idDictionary: Int)
    suspend fun removeFromFavorites(userId: String, idDictionary: Int)
    suspend fun isFavorite(userId: String, idDictionary: Int): Boolean
    fun getFavorites(userId: String): Flow<List<DictionaryEntryWithFavorite>>
} 