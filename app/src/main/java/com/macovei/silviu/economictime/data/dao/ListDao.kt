package com.macovei.silviu.economictime.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.macovei.silviu.economictime.data.entity.ListItem
import io.reactivex.Flowable


/**
 * Created by silviumacovei on 2/21/18.
 */
@Dao
interface ListDao {
    @Query("SELECT * FROM items")
    fun all(): Flowable<List<ListItem>>

    @Query("SELECT COUNT(*) from items")
    fun count(): Int

    @Query("SELECT * FROM " + "items" + " WHERE uid == :uid")
    fun getListItemById(uid: Long): Flowable<ListItem>

    @Query("DELETE FROM " + "items")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listItem: ListItem)

    @Query("DELETE FROM " + "items" + " WHERE uid = :uid")
    fun delete(uid: Long)

}