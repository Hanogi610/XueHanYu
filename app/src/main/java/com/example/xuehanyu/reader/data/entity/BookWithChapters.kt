package com.example.xuehanyu.reader.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class BookWithChapters(
    @Embedded val bookEntity: BookEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "book_id",
    )
    val chapters: List<ChapterEntity>
)