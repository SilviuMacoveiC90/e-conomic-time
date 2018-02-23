package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.model.ListItem
import io.reactivex.Flowable

/**
 * Created by silviumacovei on 2/23/18.
 */
interface ListDataSource {
    fun loadList(): Flowable<List<ListItem>>

    fun addListItem(listItem: ListItem)

    fun deleteListItem(listItem: ListItem)

    fun clearData()

}