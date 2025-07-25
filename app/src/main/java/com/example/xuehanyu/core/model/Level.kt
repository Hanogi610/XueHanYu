package com.example.xuehanyu.core.model

enum class Level {
    HSK1,
    HSK2,
    HSK3,
    HSK4,
    HSK5,
    HSK6,
    HSK7,
    HSK8,
    HSK9,
    UNKNOWN;

    companion object{
        fun fromString(level: String): Level {
            return when (level) {
                "HSK1" -> HSK1
                "HSK2" -> HSK2
                "HSK3" -> HSK3
                "HSK4" -> HSK4
                "HSK5" -> HSK5
                "HSK6" -> HSK6
                "HSK7" -> HSK7
                "HSK8" -> HSK8
                "HSK9" -> HSK9
                else -> UNKNOWN
            }
        }
    }
}