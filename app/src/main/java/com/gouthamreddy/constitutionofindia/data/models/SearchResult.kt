package com.gouthamreddy.constitutionofindia.data.models

sealed class SearchResult {
    data class ArticleResult(val article: ArticleEntity) : SearchResult()
    data class ScheduleResult(val schedule: ScheduleEntity) : SearchResult()
    data class AmendmentResult(val amendment: AmendmentEntity) : SearchResult()
}
