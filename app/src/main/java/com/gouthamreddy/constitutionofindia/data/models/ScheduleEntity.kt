package com.gouthamreddy.constitutionofindia.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "articles") val articles: String,
    @ColumnInfo(name = "details") val details: List<SchedulesResponse.Detail>
)

// TypeConverter for List<SchedulesResponse.Detail>
class ScheduleDetailListConverter {
    @TypeConverter
    fun fromDetailList(value: List<SchedulesResponse.Detail>): String {
        return Json.encodeToString(value)
    }

    @TypeConverter
    fun toDetailList(value: String): List<SchedulesResponse.Detail> {
        return Json.decodeFromString(value)
    }
}