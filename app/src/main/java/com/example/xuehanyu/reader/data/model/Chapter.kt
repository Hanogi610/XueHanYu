package com.example.xuehanyu.reader.data.model

import com.example.xuehanyu.reader.data.entity.ChapterEntity

data class Chapter(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val order: Int,
)

fun ChapterEntity.toChapter(): Chapter {
    return Chapter(
        id = this.id,
        title = this.title,
        content = this.content,
        order = this.order
    )
}

fun Chapter.toEntity(bookId: Long): ChapterEntity {
    return ChapterEntity(
        id = this.id,
        bookId = bookId,
        title = this.title,
        content = this.content,
        order = this.order
    )
}