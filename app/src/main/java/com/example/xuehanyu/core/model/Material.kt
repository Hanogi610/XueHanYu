package com.example.xuehanyu.core.model

import java.util.Date

abstract class Material {
    open var id: Long = 0L
    abstract val title: String
    abstract val author: String
    abstract val releaseDate: String
    abstract val lastUpdated: String
    abstract val viewCount: Int
    abstract val cover: String
    abstract val bigCover: String
    abstract val description: String
    open var level: Level = Level.UNKNOWN
}
