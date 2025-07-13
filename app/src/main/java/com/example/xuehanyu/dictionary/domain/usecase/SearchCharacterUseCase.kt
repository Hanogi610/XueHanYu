package com.example.xuehanyu.dictionary.domain.usecase

import com.example.xuehanyu.dictionary.data.repository.DictionaryRepository
import javax.inject.Inject

class SearchCharacterUseCase @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend operator fun invoke(character: String) = dictionaryRepository.searchCharacter(character)
} 