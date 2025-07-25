package com.example.xuehanyu.reader.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.xuehanyu.core.model.Material
import com.example.xuehanyu.core.model.Type
import com.example.xuehanyu.core.sample.NovelDummy
import com.example.xuehanyu.reader.data.model.Book
import com.example.xuehanyu.reader.data.model.Chapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReaderViewModel : ViewModel() {

    private val _material = MutableStateFlow<Material?>(null)
    val material = _material.asStateFlow()

    private val _chapter = MutableStateFlow<Chapter?>(null)
    val chapter = _chapter.asStateFlow()

    suspend fun getMaterial(materialId: Long) {
        _material.value = NovelDummy.getDummyNovels()[materialId.toInt()-1]
    }

    suspend fun getChapter(chapterId: Long) {
        if(_material.value != null && _material.value is Book && (_material.value as Book).type == Type.NOVEL) {
            val book = _material.value as Book
            _chapter.value = book.chapters.find { it.id == chapterId }
        } else {
            _chapter.value = null // Handle case where material is not a book or has no chapters
        }
    }

    fun toggleBookmark() {
        // Implement bookmark toggling logic here
    }

    fun shareBook() {
    }

    fun toggleReadingSetting() {
    }

    fun hasNextChapter(): Long? {
        // Implement logic to check if the next chapter is available
        return null
    }

    fun hasPreviousChapter(): Long? {
        // Implement logic to check if the previous chapter is available
        return null
    }
}