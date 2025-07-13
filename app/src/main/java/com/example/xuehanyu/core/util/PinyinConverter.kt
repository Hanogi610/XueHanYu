package com.example.xuehanyu.core.util

object PinyinConverter {
    
    private val toneMap = mapOf(
        'a' to listOf("ā", "á", "ǎ", "à"),
        'e' to listOf("ē", "é", "ě", "è"),
        'i' to listOf("ī", "í", "ǐ", "ì"),
        'o' to listOf("ō", "ó", "ǒ", "ò"),
        'u' to listOf("ū", "ú", "ǔ", "ù"),
        'ü' to listOf("ǖ", "ǘ", "ǚ", "ǜ")
    )

    /**
     * Converts Pinyin with tone numbers to Pinyin with tone marks
     * Example: "hao3" → "hǎo", "ni3 hao3" → "nǐ hǎo"
     */
    fun convertPinyinWithNumberToTone(pinyin: String): String {
        if (pinyin.isBlank()) return pinyin
        
        return pinyin
            .split(" ")
            .joinToString(" ") { markSyllable(it) }
    }

    private fun markSyllable(syllable: String): String {
        // Check if the last character is a digit (tone number)
        val tone = syllable.last().digitToIntOrNull() ?: return syllable
        if (tone !in 1..4) return syllable.dropLastWhile { it.isDigit() }

        val base = syllable.dropLast(1)
        if (base.isEmpty()) return syllable

        // Find the target vowel for tone marking
        val targetVowel = findTargetVowel(base) ?: return base

        val toneChar = toneMap[targetVowel]?.get(tone - 1) ?: return base
        return base.replaceFirst(targetVowel.toString(), toneChar)
    }

    private fun findTargetVowel(syllable: String): Char? {
        // Priority order for tone marking: a > e > o > i > u > ü
        return when {
            'a' in syllable -> 'a'
            'e' in syllable -> 'e'
            'o' in syllable -> 'o'
            'i' in syllable -> 'i'
            'u' in syllable -> 'u'
            'ü' in syllable -> 'ü'
            else -> null
        }
    }
} 