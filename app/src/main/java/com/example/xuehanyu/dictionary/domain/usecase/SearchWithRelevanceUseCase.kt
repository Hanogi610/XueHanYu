package com.example.xuehanyu.dictionary.domain.usecase

import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

/**
 * Use case for searching dictionary entries with relevance-based sorting
 * Results are sorted by how well they match the query:
 * 1. Exact character matches (highest priority)
 * 2. Character prefix matches
 * 3. Exact pinyin matches
 * 4. Pinyin prefix matches
 * 5. Meaning contains query (lowest priority)
 * Within each relevance level, results are sorted by frequency
 */
class SearchWithRelevanceUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(query: String, limit: Int = 50): List<DictionaryEntry> {
        return dictionaryRepository.searchAll(query).take(limit)
    }
} 