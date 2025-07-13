package com.example.xuehanyu.dictionary.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.xuehanyu.dictionary.data.local.dao.DictionaryDao
import com.example.xuehanyu.dictionary.data.local.dao.FavoriteDao
import com.example.xuehanyu.dictionary.data.local.entity.DictionaryEntity
import com.example.xuehanyu.dictionary.data.local.entity.FavoriteEntity

@Database(
    entities = [DictionaryEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DictionaryDatabase : RoomDatabase() {
    
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun favoriteDao(): FavoriteDao
    
    companion object {
        @Volatile
        private var INSTANCE: DictionaryDatabase? = null
        
        fun getDatabase(context: Context): DictionaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DictionaryDatabase::class.java,
                    "dictionary_database"
                )
                .createFromAsset("databases/cedict_room.db")
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
        
        private val MIGRATION_1_2 = object : androidx.room.migration.Migration(1, 2) {
            override fun migrate(database: androidx.sqlite.db.SupportSQLiteDatabase) {
                // Create the favorites table with foreign key to dictionary ID
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `favorites` (
                        `idFavorite` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `userId` TEXT NOT NULL,
                        `idDictionary` INTEGER NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        FOREIGN KEY(`idDictionary`) REFERENCES `cedict`(`idDictionary`) ON DELETE CASCADE
                    )
                """)
                
                // Create indices for better performance
                database.execSQL("CREATE INDEX IF NOT EXISTS `idx_favorite_dictionary_id` ON `favorites` (`idDictionary`)")
                database.execSQL("CREATE INDEX IF NOT EXISTS `idx_favorite_user_id` ON `favorites` (`userId`)")
            }
        }
    }
} 