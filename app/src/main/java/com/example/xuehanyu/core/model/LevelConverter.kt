package com.example.xuehanyu.core.model

import androidx.room.TypeConverter

class LevelConverter {

    @TypeConverter
    fun fromLevel(level: Level): String {
        return level.name
    }

    @TypeConverter
    fun toLevel(level: String): Level {
        return Level.fromString(level)
    }
}
