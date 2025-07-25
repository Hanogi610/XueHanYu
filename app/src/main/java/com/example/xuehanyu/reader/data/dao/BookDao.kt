package com.example.xuehanyu.reader.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.xuehanyu.reader.data.entity.BookWithChapters
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Transaction
    @Query("SELECT * FROM book WHERE id = :bookId")
    fun getBookWithChapters(bookId: Long): Flow<BookWithChapters?>

//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    suspend fun insertBook(book: BookWithChapters): Long
}