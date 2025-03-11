package com.gouthamreddy.constitutionofindia.mappers

import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ConstitutionCombinedResponseItem

class ArticleJsonToEntityMapper : Mapper<ConstitutionCombinedResponseItem, ArticleEntity> {
    override fun map(input: ConstitutionCombinedResponseItem): ArticleEntity {
        return ArticleEntity(
            title = input.title,
            url = input.url,
            part = input.part,
            articleNumber = input.content.articleNumber,
            introContent = input.content.introContent,
            version1 = input.content.version1,
            version2 = input.content.version2,
            summary = input.content.summary
        )
    }
}