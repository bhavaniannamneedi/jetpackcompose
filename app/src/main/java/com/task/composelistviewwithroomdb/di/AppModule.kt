package com.bharath.roomguide.di


import android.app.Application
import androidx.room.Room
import com.task.composelistviewwithroomdb.data.datasource.MyDatabase
import com.task.composelistviewwithroomdb.data.repository.Repository
import com.task.composelistviewwithroomdb.data.repository.RepositoryImpl

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun provideMyDataBase(app: Application): MyDatabase {
        return Room.databaseBuilder(
            app,
            MyDatabase::class.java,
            "DataInfoEntity"
        )
//            .addMigrations() later add migrations if u change the table
            .build()
    }



    @Provides
    @Singleton
    fun provideMyRepository(mydb:MyDatabase) : Repository {
        return RepositoryImpl(mydb.dao)
    }
}