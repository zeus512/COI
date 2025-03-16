package com.gouthamreddy.constitutionofindia.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entities

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "article_number") val articleNumber: String, // From content object
    @ColumnInfo(name = "title") val title: String, // From top-level JSON
    @ColumnInfo(name = "url") val url: String, // From top-level JSON
    @ColumnInfo(name = "part") val part: String, // From top-level JSON
    @ColumnInfo(name = "intro_content") val introContent: String, // From content object
    @ColumnInfo(name = "version_1") val version1: String, // From content object
    @ColumnInfo(name = "version_2") val version2: String, // From content object
    @ColumnInfo(name = "summary") val summary: String // From content object

)




