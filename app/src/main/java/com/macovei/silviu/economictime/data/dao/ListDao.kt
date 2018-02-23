package com.macovei.silviu.economictime.data.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.macovei.silviu.economictime.data.model.ListItem
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
    fun getListItemById(uid: Int): Flowable<ListItem>

    @Query("DELETE FROM " + "items")
    fun deleteAll()

    @Insert
    fun insert(vararg listItem: ListItem)

    @Delete
    fun delete(vararg listItem: ListItem)

}