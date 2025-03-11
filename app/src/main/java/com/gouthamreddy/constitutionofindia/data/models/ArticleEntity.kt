package com.gouthamreddy.constitutionofindia.data.models

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

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

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "schedule_number") val scheduleNumber: String,
    @ColumnInfo(name = "content") val content: String
)

@Entity(tableName = "amendments")
data class AmendmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "amendment_number") val amendmentNumber: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "content") val content: String
)


