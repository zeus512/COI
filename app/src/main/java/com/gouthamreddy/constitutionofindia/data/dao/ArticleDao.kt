package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticle(article: ArticleEntity)

    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Delete
    suspend fun deleteArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles ORDER BY CAST(article_number AS INTEGER) ASC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()

    // Optional: Add if you need to find by article number
    @Query("SELECT * FROM articles WHERE article_number = :articleNumber")
    fun getArticleByNumber(articleNumber: String): Flow<ArticleEntity?>
}

