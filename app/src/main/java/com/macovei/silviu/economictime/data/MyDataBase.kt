package com.macovei.silviu.economictime.data

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.Database
import android.arch.persistence.room.DatabaseConfiguration
import android.arch.persistence.room.InvalidationTracker
import android.arch.persistence.room.RoomDatabase
import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.model.ListItem

/**
 * Created by silviumacovei on 2/21/18.
 */
@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun listDao(): ListDao

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper? {
        return null
    }

    override fun createInvalidationTracker(): InvalidationTracker? {
        return null
    }
}