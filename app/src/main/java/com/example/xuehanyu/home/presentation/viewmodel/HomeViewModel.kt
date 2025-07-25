package com.example.xuehanyu.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.xuehanyu.core.sample.NovelDummy
import com.example.xuehanyu.reader.data.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books get() = _books.asStateFlow()

    init {
        getHomeList()
    }

    private fun getHomeList(){
        _books.value = NovelDummy.getDummyNovels()
    }
}