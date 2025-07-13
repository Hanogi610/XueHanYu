package com.example.xuehanyu.dictionary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xuehanyu.core.theme.XueHanYuTheme
import com.example.xuehanyu.core.util.PinyinConverter
import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.presentation.component.FavoriteButton
import com.example.xuehanyu.dictionary.presentation.viewmodel.DictionaryViewModel

@Composable
fun DictionaryScreen(
    modifier: Modifier = Modifier,
    viewModel: DictionaryViewModel = hiltViewModel()
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Chinese Dictionary",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Search Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChange,
                    label = { Text("Enter Chinese character or English") },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { viewModel.searchAll() })
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = viewModel::searchAll,
                    enabled = !isLoading && searchQuery.isNotEmpty()
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
            
            // Hint text
            Text(
                text = "Press Enter or tap the search button to search",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stats Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Favorites: ${viewModel.getFavoriteCount()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "History: ${searchHistory.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Search History Section (if not searching and no results)
            if (searchHistory.isNotEmpty() && searchResults.isEmpty() && !isLoading && searchQuery.isEmpty()) {
                Text(
                    text = "Recent Searches",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchHistory.toList()) { historyItem ->
                        SuggestionChip(
                            onClick = { 
                                viewModel.onSearchQueryChange(historyItem)
                                viewModel.searchAll()
                            },
                            label = { Text(historyItem) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Loading and Error States
            if (isLoading) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            
            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Search Results
            if (searchResults.isNotEmpty()) {
                Text(
                    text = "Search Results (${searchResults.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchResults) { entryWithFavorite ->
                        DictionaryEntryCard(
                            entryWithFavorite = entryWithFavorite,
                            onToggleFavorite = { 
                                android.util.Log.d("DictionaryScreen", "Toggling favorite for entry: ${entryWithFavorite.entry.character} (ID: ${entryWithFavorite.entry.idDictionary})")
                                viewModel.toggleFavorite(entryWithFavorite.entry.idDictionary)
                            }
                        )
                    }
                }
            } else if (!isLoading && searchQuery.isNotEmpty()) {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DictionaryEntryCard(
    entryWithFavorite: com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = entryWithFavorite.character,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                FavoriteButton(
                    isFavorite = entryWithFavorite.isFavorite,
                    onToggle = onToggleFavorite
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = entryWithFavorite.pinyinWithTones,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = entryWithFavorite.meaning,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DictionaryScreenPreview() {
    XueHanYuTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Chinese Dictionary",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sample search field
            OutlinedTextField(
                value = "你好",
                onValueChange = {},
                label = { Text("Enter Chinese character or English") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                readOnly = true
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sample result
            DictionaryEntryCard(
                entryWithFavorite = com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite(
                    entry = DictionaryEntry(
                        idDictionary = 1,
                        traditional = "你",
                        simplified = "你",
                        pinyin = "ni3",
                        meaning = "you (singular)",
                        strokeCount = 7,
                        radical = "亻"
                    ),
                    isFavorite = true
                ),
                onToggleFavorite = {}
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            DictionaryEntryCard(
                entryWithFavorite = com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite(
                    entry = DictionaryEntry(
                        idDictionary = 2,
                        traditional = "好",
                        simplified = "好",
                        pinyin = "hao3",
                        meaning = "good, well, fine",
                        strokeCount = 6,
                        radical = "女"
                    ),
                    isFavorite = false
                ),
                onToggleFavorite = {}
            )
        }
    }
}