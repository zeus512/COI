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
    @ColumnInfo(name = "article_number") val articleNumber: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "part") val part: String, //e.g., Part I, Part II
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

// DAOs
@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticle(article: ArticleEntity)

    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Insert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)

    @Query("SELECT * FROM schedules")
    fun getAllSchedules(): Flow<List<ScheduleEntity>>
    
     @Query("DELETE FROM schedules")
    suspend fun deleteAllSchedules()
}

@Dao
interface AmendmentDao {
    @Insert
    suspend fun insertAmendment(amendment: AmendmentEntity)

    @Insert
    suspend fun insertAmendments(amendments: List<AmendmentEntity>)

    @Delete
    suspend fun deleteAmendment(amendment: AmendmentEntity)

    @Query("SELECT * FROM amendments")
    fun getAllAmendments(): Flow<List<AmendmentEntity>>
    
     @Query("DELETE FROM amendments")
    suspend fun deleteAllAmendments()
}

// Room Database
@Database(entities = [ArticleEntity::class, ScheduleEntity::class, AmendmentEntity::class], version = 1)
abstract class ConstitutionDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun amendmentDao(): AmendmentDao
}

