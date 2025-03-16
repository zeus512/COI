package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity

@Dao
interface ConstitutionDao {

    // Search Articles
    @Query("SELECT * FROM articles WHERE article_number LIKE '%' || :query || '%' OR title LIKE '%' || :query || '%' OR part LIKE '%' || :query || '%' OR summary LIKE '%' || :query || '%'")
    suspend fun searchArticles(query: String): List<ArticleEntity>

    // Search Schedules
    @Query("SELECT * FROM schedules WHERE title LIKE '%' || :query || '%' OR articles LIKE '%' || :query || '%' OR details LIKE '%' || :query || '%'")
    suspend fun searchSchedules(query: String): List<ScheduleEntity>

    // Search Amendments
    @Query("SELECT * FROM amendments WHERE amendment_number LIKE '%' || :query || '%' OR year LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%'")
    suspend fun searchAmendments(query: String): List<AmendmentEntity>
}
