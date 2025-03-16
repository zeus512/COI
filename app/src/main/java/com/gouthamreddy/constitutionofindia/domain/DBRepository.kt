package com.gouthamreddy.constitutionofindia.domain

import androidx.room.withTransaction
import com.gouthamreddy.constitutionofindia.data.dao.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.PreambleEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.data.models.SearchResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConstitutionRepository @Inject constructor(
    private val database: ConstitutionDatabase,
) {
    private val articleDao = database.articleDao()
    private val scheduleDao = database.scheduleDao()
    private val amendmentDao = database.amendmentDao()
    private val preambleDao = database.preambleDao()
    private val constitutionDao = database.constitutionDao()

    // Articles
    fun getAllArticles(): Flow<List<ArticleEntity>> = articleDao.getAllArticles()

    suspend fun insertArticles(articles: List<ArticleEntity>) {
        database.withTransaction {
            articleDao.deleteAllArticles()
            articleDao.insertArticles(articles)
        }
    }

    // Schedules
    fun getAllSchedules(): Flow<List<ScheduleEntity>> = scheduleDao.getAllSchedules()

    suspend fun insertSchedules(schedules: List<ScheduleEntity>) {
        database.withTransaction {
            scheduleDao.deleteAllSchedules()
            scheduleDao.insertSchedules(schedules)
        }
    }

    // Amendments
    fun getAllAmendments(): Flow<List<AmendmentEntity>> = amendmentDao.getAllAmendments()

    suspend fun insertAmendments(amendments: List<AmendmentEntity>) {
        database.withTransaction {
            amendmentDao.deleteAllAmendments()
            amendmentDao.insertAmendments(amendments)
        }
    }

    // Preamble
    fun getPreamble(): Flow<List<PreambleEntity>> = preambleDao.getAllPreamble()

    suspend fun insertPreamble(preamble: PreambleEntity) {
        database.withTransaction {
            preambleDao.deleteAllPreamble()
            preambleDao.insertPreamble(preamble)
        }
    }

    suspend fun searchDatabase(query: String): List<SearchResult> {
        val articles = constitutionDao.searchArticles(query).map { SearchResult.ArticleResult(it) }
        val schedules =
            constitutionDao.searchSchedules(query).map { SearchResult.ScheduleResult(it) }
        val amendments =
            constitutionDao.searchAmendments(query).map { SearchResult.AmendmentResult(it) }
        return articles + schedules + amendments // Combine results
    }


}