package com.macovei.silviu.economictime.di

import android.app.Application
import android.arch.persistence.room.Room
import com.macovei.silviu.economictime.data.MyDatabase
import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.repository.ListDataSource
import com.macovei.silviu.economictime.data.repository.Local
import com.macovei.silviu.economictime.data.repository.local.ListLocalDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by silviumacovei on 2/21/18.
 */
@Module
class AppModule {

    @AppScope
    @Provides
    fun provideMyDatabase(application: Application): MyDatabase {
        return Room.databaseBuilder(application, MyDatabase::class.java, "my-db")
                .build()
    }

    @AppScope
    @Provides
    fun provideListDao(myDatabase: MyDatabase): ListDao {
        return myDatabase.listDao()
    }

    @AppScope
    @Provides
    @Local
    fun provideLocalDataSource(listDao: ListDao): ListDataSource {
        return ListLocalDataSource(listDao)
    }

}