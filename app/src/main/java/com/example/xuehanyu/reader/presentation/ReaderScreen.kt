package com.example.xuehanyu.reader.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xuehanyu.reader.data.model.Chapter
import com.example.xuehanyu.reader.presentation.viewmodel.ReaderViewModel

@Composable
fun ReadingScreen(
    materialId: Long,
    chapterId: Long,
    onBack: () -> Unit,
    onChapterClick : (Long, Long) -> Unit,
    vm: ReaderViewModel = hiltViewModel()
) {
    LaunchedEffect(materialId, chapterId) {
        vm.getMaterial(materialId)
        vm.getChapter(chapterId)
    }
    val material = vm.material.collectAsState()
    val chapter = vm.chapter.collectAsState()
    ReadingScreenStateLess(
        onBack = onBack,
        onBookmarkClick = vm::toggleBookmark,
        onShareClick = vm::shareBook,
        onSettingsClick = vm::toggleReadingSetting,
        onNextChapterClick = {
            if(vm.hasNextChapter() != null)
                 onChapterClick(material.value!!.id, chapter.value?.id?.plus(1) ?: 0L) else {} },
        onPreviousChapterClick = {
            if(vm.hasPreviousChapter() != null)
                onChapterClick(material.value!!.id, chapter.value?.id?.minus(1) ?: 0L) else {} },
        onChapterListClick = {Unit},
        chapter = chapter.value,
    )
}

@Composable
fun ReadingScreenStateLess(
    onBack: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onNextChapterClick: () -> Unit,
    onPreviousChapterClick: () -> Unit,
    onChapterListClick: () -> Unit,
    chapter: Chapter?,
) {
    SelectionContainer(
        modifier = Modifier.fillMaxWidth()
    ) {
        if(chapter != null){
            Text(
                text = chapter.content
            )
        }
    }
}