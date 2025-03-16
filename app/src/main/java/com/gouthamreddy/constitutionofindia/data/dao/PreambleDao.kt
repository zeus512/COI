package com.gouthamreddy.constitutionofindia.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gouthamreddy.constitutionofindia.data.models.PreambleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PreambleDao {
    @Insert
    suspend fun insertPreamble(preamble: PreambleEntity)

    @Delete
    suspend fun deletePreamble(preamble: PreambleEntity)

    @Query("SELECT * FROM preamble")
    fun getAllPreamble(): Flow<List<PreambleEntity>>
    
     @Query("DELETE FROM preamble")
    suspend fun deleteAllPreamble()
}
