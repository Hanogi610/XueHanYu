package com.example.xuehanyu.dictionary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.xuehanyu.dictionary.data.local.entity.DictionaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DictionaryDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntries(entries: List<DictionaryEntity>)
    
    @Query("SELECT * FROM cedict WHERE simplified LIKE :query OR traditional LIKE :query LIMIT :limit")
    suspend fun searchByCharacter(query: String, limit: Int = 50): List<DictionaryEntity>
    
    @Query("SELECT * FROM cedict WHERE pinyin LIKE :query LIMIT :limit")
    suspend fun searchByPinyin(query: String, limit: Int = 50): List<DictionaryEntity>
    
    @Query("SELECT * FROM cedict WHERE definition LIKE :query LIMIT :limit")
    suspend fun searchByEnglish(query: String, limit: Int = 50): List<DictionaryEntity>
    
    @Query("SELECT * FROM cedict WHERE simplified = :character OR traditional = :character LIMIT 1")
    suspend fun getExactMatch(character: String): DictionaryEntity?
    
    @Query("SELECT * FROM cedict WHERE idDictionary = :idDictionary LIMIT 1")
    suspend fun getExactMatchById(idDictionary: Int): DictionaryEntity?
    
    @Query("SELECT COUNT(*) FROM cedict")
    suspend fun getEntryCount(): Int
    
    @Query("SELECT * FROM cedict ORDER BY ROWID LIMIT :limit")
    suspend fun getMostFrequent(limit: Int = 20): List<DictionaryEntity>
    
    @Query("SELECT * FROM cedict WHERE simplified LIKE :query OR traditional LIKE :query OR pinyin LIKE :query OR definition LIKE :query LIMIT :limit")
    suspend fun searchAll(query: String, limit: Int = 50): List<DictionaryEntity>
    
    @Query("""
        SELECT *, 
        CASE 
            WHEN simplified = :query OR traditional = :query THEN 1
            WHEN simplified LIKE :query || '%' OR traditional LIKE :query || '%' THEN 2
            WHEN pinyin = :query THEN 3
            WHEN pinyin LIKE :query || '%' THEN 4
            WHEN definition LIKE '%' || :query || '%' THEN 5
            ELSE 6
        END as relevance_score
        FROM cedict 
        WHERE simplified LIKE '%' || :query || '%' 
           OR traditional LIKE '%' || :query || '%' 
           OR pinyin LIKE '%' || :query || '%' 
           OR definition LIKE '%' || :query || '%'
        ORDER BY relevance_score ASC, ROWID
        LIMIT :limit
    """)
    suspend fun searchAllWithRelevance(query: String, limit: Int = 50): List<DictionaryEntity>

    @Query("SELECT COUNT(*) FROM cedict")
    suspend fun getCount(): Int
} 