package com.example.xuehanyu.dictionary.domain.usecase

import android.util.Log
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    companion object {
        private const val TAG = "RemoveFromFavoritesUseCase"
    }

    suspend operator fun invoke(userId: String, idDictionary: Int) {
        try {
            Log.d(TAG, "Removing from favorites - User: $userId, Dictionary ID: $idDictionary")
            dictionaryRepository.removeFromFavorites(userId, idDictionary)
            Log.d(TAG, "Successfully removed from favorites - User: $userId, Dictionary ID: $idDictionary")
        } catch (e: Exception) {
            Log.e(TAG, "Error removing from favorites - User: $userId, Dictionary ID: $idDictionary", e)
            throw e
        }
    }
} 