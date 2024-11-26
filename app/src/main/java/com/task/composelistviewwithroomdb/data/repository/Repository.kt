package com.task.composelistviewwithroomdb.data.repository

import com.task.composelistviewwithroomdb.data.datasource.MyDao
import com.task.composelistviewwithroomdb.data.entity.DataEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface Repository {
    suspend fun insert(studentEntity: DataEntity)

    suspend fun delete(studentEntity: DataEntity)

    suspend fun update(studentEntity: DataEntity)

    suspend fun getAllRecord(): Flow<List<DataEntity>>
}
class RepositoryImpl @Inject constructor(
    private val dao: MyDao,
) : Repository {
    override suspend fun insert(studentEntity: DataEntity) {
        withContext(IO) {
            dao.insert(studentEntity)
        }
    }

    override suspend fun delete(studentEntity: DataEntity) {
        withContext(IO) {
            dao.delete(studentEntity)
        }
    }

    override suspend fun update(studentEntity: DataEntity) {
        withContext(IO) {
            dao.update(studentEntity)
        }
    }

    override suspend fun getAllRecord(): Flow<List<DataEntity>> {
        return withContext(IO) {
            dao.getAllRecord()
        }
    }



}