package com.example.xuehanyu.dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorites",
    foreignKeys = [
        ForeignKey(
            entity = DictionaryEntity::class,
            parentColumns = ["idDictionary"],
            childColumns = ["idDictionary"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["idDictionary"], name = "idx_favorite_dictionary_id"),
        Index(value = ["userId"], name = "idx_favorite_user_id")
    ]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val idFavorite: Long = 0,
    val userId: String, // For future multi-user support
    val idDictionary: Int,
    val createdAt: Long = System.currentTimeMillis()
) 