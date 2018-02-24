package com.macovei.silviu.economictime.data.repository.local

import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.data.repository.ListDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

/**
 * Created by silviumacovei on 2/23/18.
 */
class ListLocalDataSource
constructor(private val listDao: ListDao) : ListDataSource {


    override fun getItem(uid: Long): Flowable<ListItem> {
        return listDao.getListItemById(uid)
    }

    override fun loadList(): Flowable<List<ListItem>> {
        return listDao.all()
    }

    override fun addListItem(listItem: ListItem): Completable {
        // Insert new one
        return Completable.fromAction { listDao.insert(listItem) }
                .subscribeOn(Schedulers.io())
    }


    override fun deleteListItem(uid: Long): Completable {
        // Delete specific element
        return Completable.fromAction { listDao.delete(uid) }
                .subscribeOn(Schedulers.io())
    }


    override fun clearData(): Completable {
        // Clear old data
        return Completable.fromAction { listDao.deleteAll() }
                .subscribeOn(Schedulers.io())
    }
}