package com.example.xuehanyu.dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cedict",
    indices = [
        Index(value = ["traditional"], name = "idx_traditional"),
        Index(value = ["simplified"], name = "idx_simplified"),
        Index(value = ["pinyin"], name = "idx_pinyin"),
        Index(value = ["definition"], name = "idx_definition")
    ]
)
data class DictionaryEntity(
    @PrimaryKey
    val idDictionary: Int,
    val traditional: String,
    val simplified: String,
    val pinyin: String,
    val definition: String
) 