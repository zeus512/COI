package com.gouthamreddy.constitutionofindia.domain

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.room.withTransaction
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionDatabase
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.utils.ConstitutionParser
import com.gouthamreddy.constitutionofindia.utils.PdfParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConstitutionRepository @Inject constructor(
    private val database: ConstitutionDatabase,
    private val pdfParser: PdfParser,
    private val constitutionParser: ConstitutionParser,
    @ApplicationContext private val context: Context
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