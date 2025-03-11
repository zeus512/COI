package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import com.gouthamreddy.constitutionofindia.data.models.ArticleEntity
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity

// Room Database
@Database(
    entities = [ArticleEntity::class, ScheduleEntity::class, AmendmentEntity::class],
    version = 1
)
abstract class ConstitutionDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun amendmentDao(): AmendmentDao
}

