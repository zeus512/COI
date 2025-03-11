package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gouthamreddy.constitutionofindia.data.models.AmendmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AmendmentDao {
    @Insert
    suspend fun insertAmendment(amendment: AmendmentEntity)

    @Insert
    suspend fun insertAmendments(amendments: List<AmendmentEntity>)

    @Delete
    suspend fun deleteAmendment(amendment: AmendmentEntity)

    @Query("SELECT * FROM amendments")
    fun getAllAmendments(): Flow<List<AmendmentEntity>>
    
     @Query("DELETE FROM amendments")
    suspend fun deleteAllAmendments()
}
