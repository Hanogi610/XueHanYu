package com.example.xuehanyu.reader.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.xuehanyu.core.model.Level
import com.example.xuehanyu.core.model.LevelConverter
import com.example.xuehanyu.core.model.Material
import com.example.xuehanyu.core.model.Type
import java.util.Date

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long = 0L,
    override val title: String,
    override val author: String,
    @ColumnInfo(name = "release_date") override val releaseDate: String,
    @ColumnInfo(name = "last_updated") override val lastUpdated: String,
    @ColumnInfo(name = "view_count") override val viewCount: Int,
    override val cover: String,
    @ColumnInfo(name = "big_cover") override val bigCover: String,
    @ColumnInfo(name = "type")
    @TypeConverters(com.example.xuehanyu.core.model.TypeConverter::class)
    var type: Type,
    @ColumnInfo(name = "description") override val description: String,
    @TypeConverters(LevelConverter::class)
    override var level: Level
) : Material()