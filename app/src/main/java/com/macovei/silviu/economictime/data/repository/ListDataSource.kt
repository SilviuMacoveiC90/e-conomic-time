package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.entity.ListItem
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by silviumacovei on 2/23/18.
 */
interface ListDataSource {
    fun loadList(): Flowable<List<ListItem>>

    fun addListItem(listItem: ListItem): Completable

    fun deleteListItem(uid: Long): Completable

    fun clearData(): Completable

    fun getItem(uid: Long): Flowable<ListItem>

}