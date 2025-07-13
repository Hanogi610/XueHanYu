package com.example.xuehanyu.dictionary.domain.usecase

import android.util.Log
import com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

class SearchWithFavoritesUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    companion object {
        private const val TAG = "SearchWithFavoritesUseCase"
    }

    suspend operator fun invoke(query: String, userId: String): List<DictionaryEntryWithFavorite> {
        try {
            Log.d(TAG, "Searching with favorites - Query: '$query', User: $userId")
            val results = dictionaryRepository.searchAllWithFavorites(query, userId)
            Log.d(TAG, "Found ${results.size} results with favorites for query: '$query'")
            return results
        } catch (e: Exception) {
            Log.e(TAG, "Error searching with favorites - Query: '$query', User: $userId", e)
            throw e
        }
    }
} 