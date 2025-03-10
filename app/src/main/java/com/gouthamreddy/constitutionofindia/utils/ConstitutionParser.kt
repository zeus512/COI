package com.gouthamreddy.constitutionofindia.utils


import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume

class ConstitutionParser @Inject constructor(@ApplicationContext val context: Context) {

    fun parseArticles(pdfText: String): List<ArticleEntity> {
        val articleRegex = Regex(
            """Article\s+(\d+\w*)\s+-\s+(.+?)(?=\s+Article\s+\d+\w*|Schedule|Amendment|$)""",
            RegexOption.DOT_MATCHES_ALL
        )
        return articleRegex.findAll(pdfText).map { matchResult ->
            val articleNumber = matchResult.groupValues[1].trim()
            val content = matchResult.groupValues[2].trim()
            val part = getPartForArticle(articleNumber, pdfText)
            ArticleEntity(articleNumber = articleNumber, content = content, part = part)
        }.toList()
    }

    fun parseSchedules(pdfText: String): List<ScheduleEntity> {
        val scheduleRegex = Regex(
            """Schedule\s+(.+?)(?:\s+-\s+)(.+?)(?=\s+Schedule|Amendment|$)""",
            RegexOption.DOT_MATCHES_ALL
        )
        return scheduleRegex.findAll(pdfText).map { matchResult ->
            val scheduleNumber = matchResult.groupValues[1].trim()
            val content = matchResult.groupValues[2].trim()
            ScheduleEntity(scheduleNumber = scheduleNumber, content = content)
        }.toList()
    }

    fun parseAmendments(pdfText: String): List<AmendmentEntity> {
        val amendmentRegex = Regex(
            """(?:Amendment)\s+(\d+)\s+(?:Act,)\s+(\d+)(?:\n)((.|\n)*?)(?=(?:Amendment|$)|\Z)""",
            RegexOption.DOT_MATCHES_ALL
        )
        return amendmentRegex.findAll(pdfText).map { matchResult ->
            val amendmentNumber = matchResult.groupValues[1].trim()
            val year = matchResult.groupValues[2].trim().toInt()
            val content = matchResult.groupValues[3].trim()
            AmendmentEntity(amendmentNumber = amendmentNumber, year = year, content = content)
        }.toList()
    }

    private fun getPartForArticle(articleNumber: String, pdfText: String): String {
        val partRegex = Regex("""Part\s+([IVX]+)\s+-\s+([^.]+)\.""")
        val matches = partRegex.findAll(pdfText)
        val partList = mutableListOf<Pair<String, String>>()
        for (match in matches) {
            val partNumber = match.groupValues[1].trim()
            val partDescription = match.groupValues[2].trim()
            partList.add(Pair(partNumber, partDescription))
        }

        val articleIndex = pdfText.indexOf("Article $articleNumber")
        if (articleIndex == -1) {
            return "Unknown"
        }

        var closestPart: Pair<String, String>? = null
        var minDistance = Int.MAX_VALUE

        for ((partNumber, partDescription) in partList) {
            val partIndex = pdfText.indexOf("Part $partNumber - $partDescription")
            if (partIndex != -1 && partIndex < articleIndex) {
                val distance = articleIndex - partIndex
                if (distance < minDistance) {
                    minDistance = distance
                    closestPart = Pair(partNumber, partDescription)
                }
            }
        }

        return closestPart?.let { "Part ${it.first} - ${it.second}" } ?: "Unknown"
    }

    suspend fun waitForDownloadCompletion(
        downloadId: Long
    ): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext suspendCancellableCoroutine { continuation ->
                val receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if (id == downloadId) {
                            val query = DownloadManager.Query().setFilterById(downloadId)
                            val downloadManager =
                                context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                            val cursor = downloadManager.query(query)
                            if (cursor.moveToFirst()) {
                                val status =
                                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                    continuation.resume(true)
                                } else {
                                    continuation.resume(false)
                                }
                            } else {
                                continuation.resume(false)
                            }
                            context.unregisterReceiver(this)
                        }
                    }
                }
                ContextCompat.registerReceiver(
                    context,
                    receiver,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                    ContextCompat.RECEIVER_NOT_EXPORTED
                )
                continuation.invokeOnCancellation {
                    context.unregisterReceiver(receiver)
                }
            }
        }
}