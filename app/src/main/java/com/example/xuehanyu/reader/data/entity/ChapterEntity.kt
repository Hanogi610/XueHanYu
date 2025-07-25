package com.example.xuehanyu.reader.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
data class ChapterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "book_id")
    val bookId: Long,
    val title: String,
    val content: String,
    val order: Int
)