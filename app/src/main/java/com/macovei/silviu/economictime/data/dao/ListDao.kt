package com.macovei.silviu.economictime.data.dao

import android.arch.persistence.room.*
import com.macovei.silviu.economictime.data.entity.AdministrationItem
import io.reactivex.Flowable


/**
 * Created by silviumacovei on 2/21/18.
 */
@Dao
interface ListDao {
    @Query("SELECT * FROM AdministrationItems")
    fun all(): Flowable<List<AdministrationItem>>

    @Query("SELECT COUNT(*) from AdministrationItems")
    fun count(): Int

    @Query("SELECT * FROM " + "AdministrationItems" + " WHERE uid == :uid")
    fun getListItemById(uid: Long): Flowable<AdministrationItem>

    @Query("DELETE FROM " + "AdministrationItems")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(administrationItem: AdministrationItem)

    @Query("DELETE FROM " + "AdministrationItems" + " WHERE uid = :uid")
    fun delete(uid: Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateItem(item: AdministrationItem)

}