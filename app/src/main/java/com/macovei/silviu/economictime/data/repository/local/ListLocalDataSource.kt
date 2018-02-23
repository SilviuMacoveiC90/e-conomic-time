package com.macovei.silviu.economictime.data.repository.local

import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.data.repository.ListDataSource
import com.macovei.silviu.economictime.di.AppScope
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/23/18.
 */
@AppScope
class ListLocalDataSource
@Inject constructor(private val listDao: ListDao) : ListDataSource {


    override fun loadList(): Flowable<List<ListItem>> {
        return listDao.all()
    }

    override fun addListItem(listItem: ListItem) {
        // Insert new one
        listDao.insert(listItem)
    }


    override fun deleteListItem(listItem: ListItem) {
        listDao.delete(listItem)
    }


    override fun clearData() {
        // Clear old data
        listDao.deleteAll()
    }
}