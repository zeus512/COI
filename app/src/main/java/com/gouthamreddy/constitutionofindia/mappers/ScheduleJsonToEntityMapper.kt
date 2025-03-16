package com.gouthamreddy.constitutionofindia.mappers

import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import com.gouthamreddy.constitutionofindia.data.models.SchedulesResponse

class ScheduleJsonToEntityMapper : Mapper<SchedulesResponse, ScheduleEntity> {
    override fun map(input: SchedulesResponse): ScheduleEntity {
        return ScheduleEntity(
            title = input.title,
            articles = input.articles,
            details = input.details
        )
    }
}