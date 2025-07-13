package com.example.xuehanyu.dictionary.data.model

import com.example.xuehanyu.core.util.PinyinConverter

data class DictionaryEntry(
    val idDictionary: Int = 0,
    val traditional: String,
    val simplified: String,
    val pinyin: String,
    val meaning: String,
    val strokeCount: Int = 0,
    val radical: String = "",
    val exampleSentences: List<String> = emptyList(),
    val difficulty: Difficulty = Difficulty.BEGINNER
) {
    /**
     * Pinyin with tone marks instead of numbers
     * Example: "hao3" → "hǎo"
     */
    val pinyinWithTones: String
        get() = PinyinConverter.convertPinyinWithNumberToTone(pinyin)
    
    /**
     * Primary character to display (simplified)
     */
    val character: String
        get() = simplified
}

enum class Difficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
} 