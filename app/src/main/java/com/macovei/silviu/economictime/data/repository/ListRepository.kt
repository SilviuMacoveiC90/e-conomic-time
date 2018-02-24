package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.entity.ListItem
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



//    @VisibleForTesting internal var caches: MutableList<ListItem> = ArrayList()


    override fun loadList(): Flowable<List<ListItem>> {
//        if (caches.size > 0) {
//            // if cache is available, return it immediately
//            return Flowable.just<List<ListItem>>(caches)
//        } else {
        // else return data from local storage
        return localDataSource.loadList()
//                    .flatMap({ Flowable.fromIterable(it) })
//                    .doOnNext { listItem -> caches.add(listItem) }
//                    .toList()
//                    .toFlowable()
//                    .filter({ list -> !list.isEmpty() })
//        }
    }

    override fun getListItem(uid: Long): Flowable<ListItem> {
        return localDataSource.getItem(uid)
    }

    override fun addListItem(listItem: ListItem): Completable {
        return localDataSource.addListItem(listItem)
    }

    override fun clearData(): Completable {
        return localDataSource.clearData()
                .doOnComplete {}
    }

    override fun deleteListItem(uid: Long): Completable {
        return localDataSource.deleteListItem(uid)
    }

    override fun getItem(uid: Long): Flowable<ListItem> {
          return localDataSource.getItem(uid)
    }

}