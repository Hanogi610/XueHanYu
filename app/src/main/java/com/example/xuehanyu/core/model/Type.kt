package com.example.xuehanyu.core.model

enum class Type {
    ONESHOT,
    NOVEL,
    ARTICLE,
    UNKNOWN;

    companion object {
        fun fromString(type: String): Type {
            return when (type.lowercase()) {
                "oneshot" -> ONESHOT
                "novel" -> NOVEL
                "article" -> ARTICLE
                else -> UNKNOWN
            }
        }
    }
}