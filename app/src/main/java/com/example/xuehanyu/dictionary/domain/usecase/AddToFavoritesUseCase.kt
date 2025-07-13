package com.example.xuehanyu.dictionary.domain.usecase

import android.util.Log
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

class AddToFavoritesUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    companion object {
        private const val TAG = "AddToFavoritesUseCase"
    }

    suspend operator fun invoke(userId: String, idDictionary: Int) {
        try {
            Log.d(TAG, "Adding to favorites - User: $userId, Dictionary ID: $idDictionary")
            dictionaryRepository.addToFavorites(userId, idDictionary)
            Log.d(TAG, "Successfully added to favorites - User: $userId, Dictionary ID: $idDictionary")
        } catch (e: Exception) {
            Log.e(TAG, "Error adding to favorites - User: $userId, Dictionary ID: $idDictionary", e)
            throw e
        }
    }
} 