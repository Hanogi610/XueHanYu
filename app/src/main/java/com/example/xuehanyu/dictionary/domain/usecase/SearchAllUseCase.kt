package com.example.xuehanyu.dictionary.domain.usecase

import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

/**
 * Use case for searching dictionary entries across all fields (character, pinyin, meaning)
 * Results are automatically sorted by relevance:
 * 1. Exact character matches
 * 2. Character prefix matches
 * 3. Exact pinyin matches
 * 4. Pinyin prefix matches
 * 5. Meaning contains query
 */
class SearchAllUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(query: String): List<DictionaryEntry> {
        return dictionaryRepository.searchAll(query)
    }
} 