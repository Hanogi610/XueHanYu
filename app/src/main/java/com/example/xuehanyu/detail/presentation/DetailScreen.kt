package com.example.xuehanyu.detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.xuehanyu.core.model.Type
import com.example.xuehanyu.detail.presentation.viewmodel.DetailViewModel
import com.example.xuehanyu.reader.data.model.Book

@Composable
fun DetailScreenStateLess(
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onDownloadClick: () -> Unit,
    book: Book?,
    onItemCLick: (Long, Long) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Faded background image
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(book?.bigCover)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape),
            alpha = 0.3f
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                // Small cover image
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(book?.cover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Book cover",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Book details
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = book?.title ?: "Loading...",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = book?.author ?: "Unknown Author",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = book?.description ?: "No description available.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = book?.level.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Black.copy(alpha = 0.6f)
                    )
                }

            }
            if(book != null && book.type == Type.NOVEL){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(book.chapters.size) { index ->
                        Text(
                            text = book.chapters[index].title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(8.dp)
                                .clickable { onItemCLick(book.id, book.chapters[index].id ) } // Navigate to reader with chapter index
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(
    onItemCLick: (Long, Long) -> Unit,
    onBackClick: () -> Unit,
    bookId: Long,
    vm: DetailViewModel = hiltViewModel()
) {
    vm.getBookDetails(bookId)
    val book = vm.book.collectAsState().value
    val error = vm.errorMessage.collectAsState().value

    LaunchedEffect(error) {
        if (error != null) {
            if (error.isNotEmpty()) {
                // Handle error, e.g., show a snackbar or dialog
                // For now, we just log it
                println("Error fetching book details: $error")
            }
        }
    }

    DetailScreenStateLess(
        onBackClick = onBackClick,
        onShareClick = {Unit},
        onBookmarkClick = {Unit},
        onDownloadClick = {Unit},
        book = book,
        onItemCLick = onItemCLick
    )
}