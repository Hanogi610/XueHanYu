package com.example.xuehanyu.dictionary.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xuehanyu.core.util.SharedPrefHelper
import com.example.xuehanyu.dictionary.data.model.DictionaryEntry
import com.example.xuehanyu.dictionary.data.model.DictionaryEntryWithFavorite
import com.example.xuehanyu.dictionary.domain.usecase.AddToFavoritesUseCase
import com.example.xuehanyu.dictionary.domain.usecase.RemoveFromFavoritesUseCase
import com.example.xuehanyu.dictionary.domain.usecase.SearchAllUseCase
import com.example.xuehanyu.dictionary.domain.usecase.SearchCharacterUseCase
import com.example.xuehanyu.dictionary.domain.usecase.SearchWithFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val searchCharacterUseCase: SearchCharacterUseCase,
    private val searchAllUseCase: SearchAllUseCase,
    private val searchWithFavoritesUseCase: SearchWithFavoritesUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase,
    private val sharedPrefHelper: SharedPrefHelper
) : ViewModel() {

    companion object {
        private const val TAG = "DictionaryViewModel"
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResult = MutableStateFlow<DictionaryEntry?>(null)
    val searchResult: StateFlow<DictionaryEntry?> = _searchResult.asStateFlow()

    private val _searchResults = MutableStateFlow<List<DictionaryEntryWithFavorite>>(emptyList())
    val searchResults: StateFlow<List<DictionaryEntryWithFavorite>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _searchHistory = MutableStateFlow<Set<String>>(emptySet())
    val searchHistory: StateFlow<Set<String>> = _searchHistory.asStateFlow()

    init {
        loadSearchHistory()
        Log.d(TAG, "DictionaryViewModel initialized with user ID: ${sharedPrefHelper.getUserId()}")
    }

    private fun loadSearchHistory() {
        val history = sharedPrefHelper.getSearchHistory()
        _searchHistory.value = history
        Log.d(TAG, "Loaded search history: ${history.size} items")
    }

    fun onSearchQueryChange(query: String) {
        Log.d(TAG, "Search query changed to: '$query'")
        _searchQuery.value = query
        // Clear error when user starts typing
        if (_errorMessage.value != null) {
            Log.d(TAG, "Clearing error message")
            _errorMessage.value = null
        }
    }

    fun searchCharacter() {
        val query = _searchQuery.value.trim()
        Log.d(TAG, "Searching for character: '$query'")
        
        if (query.isEmpty()) {
            Log.w(TAG, "Empty query provided for character search")
            _errorMessage.value = "Please enter a character to search"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                Log.d(TAG, "Executing character search for: '$query'")
                val result = searchCharacterUseCase(query)
                if (result != null) {
                    Log.d(TAG, "Character found: ${result.character} (ID: ${result.idDictionary})")
                    _searchResult.value = result
                    _searchResults.value = listOf(DictionaryEntryWithFavorite(entry = result))
                    
                    // Add to search history
                    sharedPrefHelper.addToSearchHistory(query)
                    sharedPrefHelper.setLastSearch(query)
                    loadSearchHistory()
                } else {
                    Log.w(TAG, "Character not found: '$query'")
                    _errorMessage.value = "Character not found"
                    _searchResult.value = null
                    _searchResults.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error searching for character: '$query'", e)
                _errorMessage.value = "Search failed: ${e.message}"
                _searchResult.value = null
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchAll() {
        val query = _searchQuery.value.trim()
        Log.d(TAG, "Searching all for query: '$query'")
        
        if (query.isEmpty()) {
            Log.w(TAG, "Empty query provided for search")
            _errorMessage.value = "Please enter a search term"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val userId = sharedPrefHelper.getUserId()
                Log.d(TAG, "Executing search with favorites for: '$query' by user: $userId")
                val results = searchWithFavoritesUseCase(query, userId)
                Log.d(TAG, "Search completed - Found ${results.size} results")
                _searchResults.value = results
                _searchResult.value = results.firstOrNull()?.entry
                
                if (results.isEmpty()) {
                    Log.w(TAG, "No results found for query: '$query'")
                    _errorMessage.value = "No results found for '$query'"
                } else {
                    Log.d(TAG, "Search successful - ${results.size} results found")
                    
                    // Add to search history
                    sharedPrefHelper.addToSearchHistory(query)
                    sharedPrefHelper.setLastSearch(query)
                    loadSearchHistory()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error searching for query: '$query'", e)
                _errorMessage.value = "Search failed: ${e.message}"
                _searchResults.value = emptyList()
                _searchResult.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(idDictionary: Int) {
        Log.d(TAG, "Toggling favorite for dictionary ID: $idDictionary")
        viewModelScope.launch {
            try {
                val currentResults = _searchResults.value
                Log.d(TAG, "Current search results count: ${currentResults.size}")
                
                val entryIndex = currentResults.indexOfFirst { it.entry.idDictionary == idDictionary }
                Log.d(TAG, "Found entry at index: $entryIndex")
                
                if (entryIndex != -1) {
                    val entry = currentResults[entryIndex]
                    Log.d(TAG, "Entry found: ${entry.entry.character} (ID: ${entry.entry.idDictionary}), current favorite status: ${entry.isFavorite}")
                    
                    val userId = sharedPrefHelper.getUserId()
                    if (entry.isFavorite) {
                        Log.d(TAG, "Removing from favorites - ID: $idDictionary")
                        removeFromFavoritesUseCase(userId, idDictionary)
                        sharedPrefHelper.decrementFavoriteCount()
                    } else {
                        Log.d(TAG, "Adding to favorites - ID: $idDictionary")
                        addToFavoritesUseCase(userId, idDictionary)
                        sharedPrefHelper.incrementFavoriteCount()
                    }
                    
                    // Update the UI immediately for better UX
                    val updatedResults = currentResults.toMutableList()
                    updatedResults[entryIndex] = entry.copy(isFavorite = !entry.isFavorite)
                    _searchResults.value = updatedResults
                    Log.d(TAG, "UI updated - new favorite status: ${!entry.isFavorite}")
                } else {
                    Log.w(TAG, "Entry not found in current results for ID: $idDictionary")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error toggling favorite for ID: $idDictionary", e)
                _errorMessage.value = "Failed to update favorite: ${e.message}"
            }
        }
    }

    fun clearSearchHistory() {
        Log.d(TAG, "Clearing search history")
        sharedPrefHelper.clearSearchHistory()
        _searchHistory.value = emptySet()
    }

    fun getSearchHistory(): Set<String> {
        return sharedPrefHelper.getSearchHistory()
    }

    fun getFavoriteCount(): Int {
        return sharedPrefHelper.getFavoriteCount()
    }

    fun clearError() {
        Log.d(TAG, "Clearing error message")
        _errorMessage.value = null
    }
} 