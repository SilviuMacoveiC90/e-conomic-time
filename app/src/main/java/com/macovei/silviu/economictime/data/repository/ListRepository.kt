package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.entity.AdministrationItem
import com.macovei.silviu.economictime.di.AppScope
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/23/18.
 */
@AppScope
open class ListRepository @Inject constructor(
        @Local private val localDataSource: ListDataSource
) : IListRepo {

    override fun loadList(): Flowable<List<AdministrationItem>> {
        return localDataSource.loadList()
    }

    override fun getListItem(uid: Long): Flowable<AdministrationItem> {
        return localDataSource.getItem(uid)
    }

    override fun addListItem(administrationItem: AdministrationItem): Completable {
        return localDataSource.addListItem(administrationItem)
    }

    override fun clearData(): Completable {
        return localDataSource.clearData()
                .doOnComplete {}
    }

    override fun deleteListItem(uid: Long): Completable {
        return localDataSource.deleteListItem(uid)
    }

    override fun getItem(uid: Long): Flowable<AdministrationItem> {
        return localDataSource.getItem(uid)
    }

    override fun updateItem(item: AdministrationItem): Completable {
        return localDataSource.updateItem(item)
    }

}