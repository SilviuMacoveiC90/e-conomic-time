package com.macovei.silviu.economictime.di

import android.app.Application
import android.arch.persistence.room.Room
import com.macovei.silviu.economictime.data.MyDatabase
import com.macovei.silviu.economictime.data.dao.ListDao
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
                .allowMainThreadQueries()
                .build()
    }

    @AppScope
    @Provides
    fun provideUserDao(myDatabase: MyDatabase): ListDao {
        return myDatabase.listDao()
    }

}