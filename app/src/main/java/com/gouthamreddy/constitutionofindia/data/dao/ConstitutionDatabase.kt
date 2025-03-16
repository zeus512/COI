package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.PreambleEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleDetailListConverter
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity

// Room Database
@Database(
    entities = [ArticleEntity::class, ScheduleEntity::class, AmendmentEntity::class, PreambleEntity::class],
    version = 1
)

@TypeConverters(ScheduleDetailListConverter::class)
abstract class ConstitutionDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun amendmentDao(): AmendmentDao
    abstract fun preambleDao(): PreambleDao
    abstract fun constitutionDao(): ConstitutionDao
}

