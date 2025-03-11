package com.gouthamreddy.constitutionofindia.domain

import androidx.room.withTransaction
import com.gouthamreddy.constitutionofindia.data.dao.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConstitutionRepository @Inject constructor(
    private val database: ConstitutionDatabase,
) {
    private val articleDao = database.articleDao()
    private val scheduleDao = database.scheduleDao()
    private val amendmentDao = database.amendmentDao()

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


}