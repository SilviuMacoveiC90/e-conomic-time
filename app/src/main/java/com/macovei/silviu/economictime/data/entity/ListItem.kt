package com.macovei.silviu.economictime.data.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by silviumacovei on 2/21/18.
 */
@Entity(tableName = "items")
data class ListItem(
        @PrimaryKey(autoGenerate = true)
        var uid: Long? = 0,
        @field:ColumnInfo(name = "date")
        var date: String,
        @field:ColumnInfo(name = "project")
        var project: String,
        @field:ColumnInfo(name = "activity")
        var activity: String,
        @field:ColumnInfo(name = "hours")
        var hours: String,
        @field:ColumnInfo(name = "status")
        var status: String)
