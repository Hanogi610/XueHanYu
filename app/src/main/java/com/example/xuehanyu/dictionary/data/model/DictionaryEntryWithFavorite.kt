package com.example.xuehanyu.dictionary.data.model

import com.example.xuehanyu.dictionary.data.local.entity.DictionaryEntity

data class DictionaryEntryWithFavorite(
    val entry: DictionaryEntry,
    val isFavorite: Boolean = false,
    val favoriteId: Long? = null
) {
    val character: String get() = entry.character
    val pinyin: String get() = entry.pinyin
    val pinyinWithTones: String get() = entry.pinyinWithTones
    val meaning: String get() = entry.meaning
    val strokeCount: Int get() = entry.strokeCount
    val radical: String get() = entry.radical
    val exampleSentences: List<String> get() = entry.exampleSentences
    val difficulty: Difficulty get() = entry.difficulty
} 