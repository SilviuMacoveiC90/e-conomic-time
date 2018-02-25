package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.entity.AdministrationItem
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by silviumacovei on 2/23/18.
 */
interface ListDataSource {
    fun loadList(): Flowable<List<AdministrationItem>>

    fun addListItem(administrationItem: AdministrationItem): Completable

    fun deleteListItem(uid: Long): Completable

    fun clearData(): Completable

    fun getItem(uid: Long): Flowable<AdministrationItem>

    fun updateItem(item: AdministrationItem): Completable

}