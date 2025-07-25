package com.example.xuehanyu.reader.data.model

import com.example.xuehanyu.core.model.Level
import com.example.xuehanyu.core.model.Material
import com.example.xuehanyu.core.model.Type
import com.example.xuehanyu.reader.data.entity.BookEntity
import com.example.xuehanyu.reader.data.entity.BookWithChapters
import java.util.Date

data class Book(
    override var id: Long = 0L,
    override val title: String,
    override val author: String,
    override val releaseDate: String,
    override val lastUpdated: String,
    override val viewCount: Int,
    override val cover: String,
    override val bigCover: String,
    var type: Type,
    override var level: Level,
    override val description: String,
    val chapters: List<Chapter>
) : Material()

fun BookEntity.toBook(chapters: List<Chapter> = emptyList()): Book {
    return Book(
        id = id,
        title = title,
        author = author,
        releaseDate = releaseDate,
        lastUpdated = lastUpdated,
        viewCount = viewCount,
        cover = cover,
        bigCover = bigCover,
        type = type,
        level = level,
        description = description,
        chapters = chapters
    )
}

fun BookWithChapters.toBook(): Book {
    val chapters = this.chapters.map { it.toChapter() }
    return this.bookEntity.toBook(chapters)
}

fun Book.toEntity(): BookEntity {
    return BookEntity(
        id = this.id,
        title = this.title,
        author = this.author,
        releaseDate = this.releaseDate,
        lastUpdated = this.lastUpdated,
        viewCount = this.viewCount,
        cover = this.cover,
        bigCover = this.bigCover,
        type = this.type,
        level = this.level,
        description = this.description
    )
}

fun Book.toBookWithChapters(): BookWithChapters {
    return BookWithChapters(
        bookEntity = this.toEntity(),
        chapters = this.chapters.map { it.toEntity(this.id) }
    )
}