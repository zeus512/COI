package com.gouthamreddy.constitutionofindia.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "amendments")
data class AmendmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "amendment_number") val amendmentNumber: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "content") val content: String
)
