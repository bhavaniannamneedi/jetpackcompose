package com.task.composelistviewwithroomdb.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.composelistviewwithroomdb.data.entity.DataEntity

@Database(
    entities = [DataEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase(){
    abstract val dao: MyDao
}