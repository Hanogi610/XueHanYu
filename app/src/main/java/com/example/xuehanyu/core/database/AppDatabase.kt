package com.example.xuehanyu.core.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.xuehanyu.core.model.LevelConverter
import com.example.xuehanyu.dictionary.data.local.dao.DictionaryDao
import com.example.xuehanyu.dictionary.data.local.dao.FavoriteDao
import com.example.xuehanyu.dictionary.data.local.entity.DictionaryEntity
import com.example.xuehanyu.dictionary.data.local.entity.FavoriteEntity
import com.example.xuehanyu.reader.data.dao.BookDao
import com.example.xuehanyu.reader.data.entity.BookEntity
import com.example.xuehanyu.reader.data.entity.ChapterEntity

@Database(
    entities = [DictionaryEntity::class, FavoriteEntity::class, BookEntity::class, ChapterEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun dictionaryDao(): DictionaryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun bookDao(): BookDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
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