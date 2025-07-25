package com.example.xuehanyu.core.model

import androidx.room.TypeConverter

class TypeConverter {

    @TypeConverter
    fun fromType(type: Type): String {
        return type.name.lowercase()
    }

    @TypeConverter
    fun toType(type: String): Type {
        return Type.fromString(type)
    }
}