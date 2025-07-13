package com.example.xuehanyu.dictionary.data.repository

import android.util.Log
import com.example.xuehanyu.dictionary.data.local.dao.DictionaryDao
import com.example.xuehanyu.dictionary.data.local.dao.FavoriteDao
import com.example.xuehanyu.dictionary.data.local.entity.DictionaryEntity
import com.example.xuehanyu.dictionary.data.local.entity.FavoriteEntity
import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite
import com.example.xuehanyu.dictionary.data.model.Difficulty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryDao: DictionaryDao,
    private val favoriteDao: FavoriteDao
) : DictionaryRepository {

    companion object {
        private const val TAG = "DictionaryRepository"
    }

    override suspend fun searchCharacter(character: String): DictionaryEntry? {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Searching for character: $character")
                val entity = dictionaryDao.getExactMatch(character)
                if (entity != null) {
                    Log.d(TAG, "Found character: ${entity.simplified} (ID: ${entity.idDictionary})")
                } else {
                    Log.d(TAG, "Character not found: $character")
                }
                entity?.toDictionaryEntry()
            } catch (e: Exception) {
                Log.e(TAG, "Error searching for character: $character", e)
                null
            }
        }
    }

    override suspend fun searchByPinyin(pinyin: String): List<DictionaryEntry> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Searching by pinyin: $pinyin")
                val results = dictionaryDao.searchByPinyin("%$pinyin%").map { it.toDictionaryEntry() }
                Log.d(TAG, "Found ${results.size} results for pinyin: $pinyin")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error searching by pinyin: $pinyin", e)
                emptyList()
            }
        }
    }

    override suspend fun searchByMeaning(meaning: String): List<DictionaryEntry> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Searching by meaning: $meaning")
                val results = dictionaryDao.searchByEnglish("%$meaning%").map { it.toDictionaryEntry() }
                Log.d(TAG, "Found ${results.size} results for meaning: $meaning")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error searching by meaning: $meaning", e)
                emptyList()
            }
        }
    }

    override suspend fun getRandomCharacters(count: Int): List<DictionaryEntry> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Getting $count random characters")
                val results = dictionaryDao.getMostFrequent(count).map { it.toDictionaryEntry() }
                Log.d(TAG, "Retrieved ${results.size} random characters")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error getting random characters", e)
                emptyList()
            }
        }
    }

    override suspend fun getCharactersByDifficulty(difficulty: Difficulty): List<DictionaryEntry> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Getting characters by difficulty: $difficulty")
                val results = dictionaryDao.getMostFrequent(20).map { it.toDictionaryEntry() }
                Log.d(TAG, "Retrieved ${results.size} characters for difficulty: $difficulty")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error getting characters by difficulty: $difficulty", e)
                emptyList()
            }
        }
    }

    override suspend fun searchAll(query: String): List<DictionaryEntry> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Searching all for query: $query")
                val results = dictionaryDao.searchAllWithRelevance(query).map { it.toDictionaryEntry() }
                Log.d(TAG, "Found ${results.size} results for query: $query")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error searching all for query: $query", e)
                emptyList()
            }
        }
    }

    override suspend fun searchAllWithFavorites(query: String, userId: String): List<DictionaryEntryWithFavorite> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Searching with favorites for query: '$query' by user: $userId")
                val entries = dictionaryDao.searchAllWithRelevance(query)
                Log.d(TAG, "Found ${entries.size} dictionary entries")
                
                val favoriteIds = favoriteDao.getFavoriteDictionaryIds(userId).toSet()
                Log.d(TAG, "User $userId has ${favoriteIds.size} favorites: $favoriteIds")
                
                val results = entries.map { entity ->
                    val entry = entity.toDictionaryEntry()
                    val isFavorite = favoriteIds.contains(entity.idDictionary)
                    Log.d(TAG, "Entry ${entity.simplified} (ID: ${entity.idDictionary}) isFavorite: $isFavorite")
                    DictionaryEntryWithFavorite(
                        entry = entry,
                        isFavorite = isFavorite,
                        favoriteId = if (isFavorite) entity.idDictionary.toLong() else null
                    )
                }
                Log.d(TAG, "Returning ${results.size} entries with favorite status")
                results
            } catch (e: Exception) {
                Log.e(TAG, "Error searching with favorites for query: '$query' by user: $userId", e)
                emptyList()
            }
        }
    }

    override suspend fun addToFavorites(userId: String, idDictionary: Int) {
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Adding to favorites - User: $userId, Dictionary ID: $idDictionary")
                val favorite = FavoriteEntity(
                    userId = userId,
                    idDictionary = idDictionary
                )
                favoriteDao.insertFavorite(favorite)
                Log.d(TAG, "Successfully added to favorites - User: $userId, Dictionary ID: $idDictionary")
            } catch (e: Exception) {
                Log.e(TAG, "Error adding to favorites - User: $userId, Dictionary ID: $idDictionary", e)
                throw e
            }
        }
    }

    override suspend fun removeFromFavorites(userId: String, idDictionary: Int) {
        withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Removing from favorites - User: $userId, Dictionary ID: $idDictionary")
                favoriteDao.removeFavorite(userId, idDictionary)
                Log.d(TAG, "Successfully removed from favorites - User: $userId, Dictionary ID: $idDictionary")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing from favorites - User: $userId, Dictionary ID: $idDictionary", e)
                throw e
            }
        }
    }

    override suspend fun isFavorite(userId: String, idDictionary: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "Checking if favorite - User: $userId, Dictionary ID: $idDictionary")
                val count = favoriteDao.isFavorite(userId, idDictionary)
                val isFavorite = count > 0
                Log.d(TAG, "Favorite check result - User: $userId, Dictionary ID: $idDictionary, isFavorite: $isFavorite")
                isFavorite
            } catch (e: Exception) {
                Log.e(TAG, "Error checking if favorite - User: $userId, Dictionary ID: $idDictionary", e)
                false
            }
        }
    }

    override fun getFavorites(userId: String): Flow<List<DictionaryEntryWithFavorite>> {
        Log.d(TAG, "Getting favorites for user: $userId")
        return favoriteDao.getFavoritesByUser(userId).map { favorites ->
            try {
                Log.d(TAG, "Processing ${favorites.size} favorites for user: $userId")
                favorites.mapNotNull { favorite ->
                    val entity = dictionaryDao.getExactMatchById(favorite.idDictionary)
                    if (entity != null) {
                        Log.d(TAG, "Found dictionary entry for favorite - ID: ${favorite.idDictionary}, Character: ${entity.simplified}")
                        DictionaryEntryWithFavorite(
                            entry = entity.toDictionaryEntry(),
                            isFavorite = true,
                            favoriteId = favorite.idFavorite
                        )
                    } else {
                        Log.w(TAG, "Dictionary entry not found for favorite ID: ${favorite.idDictionary}")
                        null
                    }
                }.also { 
                    Log.d(TAG, "Returning ${it.size} favorite entries for user: $userId")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing favorites for user: $userId", e)
                emptyList()
            }
        }
    }

    private fun DictionaryEntity.toDictionaryEntry(): DictionaryEntry {
        return DictionaryEntry(
            idDictionary = idDictionary,
            traditional = traditional,
            simplified = simplified,
            pinyin = pinyin,
            meaning = definition,
            strokeCount = 0, // Not available in CC-CEDICT
            radical = "", // Not available in CC-CEDICT
            exampleSentences = emptyList(), // Not available in CC-CEDICT
            difficulty = Difficulty.BEGINNER // Default difficulty
        )
    }
} 