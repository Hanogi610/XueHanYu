package com.example.xuehanyu.detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.xuehanyu.core.sample.NovelDummy
import com.example.xuehanyu.reader.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel : ViewModel() {
    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun getBookDetails(bookId: Long) {
        // Simulate loading book details
        _isLoading.value = true
        // Here you would typically fetch the book details from a repository or use case
        // For now, we will simulate a successful fetch with a dummy book
        _book.value = NovelDummy.getDummyNovels().find { it.id == bookId }
        _isLoading.value = false
    }
}