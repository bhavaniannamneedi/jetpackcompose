package com.task.composelistviewwithroomdb.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.task.composelistviewwithroomdb.data.entity.DataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(studentEntity: DataEntity)

    @Delete
    suspend fun delete(studentEntity: DataEntity)

    @Update
    suspend fun update(studentEntity: DataEntity)

    @Query("SELECT * FROM DataInfoEntity")
    fun getAllRecord(): Flow<List<DataEntity>>
}