package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gouthamreddy.constitutionofindia.data.models.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Insert
    suspend fun insertSchedule(schedule: ScheduleEntity)

    @Insert
    suspend fun insertSchedules(schedules: List<ScheduleEntity>)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)

    @Query("SELECT * FROM schedules")
    fun getAllSchedules(): Flow<List<ScheduleEntity>>

    @Query("DELETE FROM schedules")
    suspend fun deleteAllSchedules()
}
