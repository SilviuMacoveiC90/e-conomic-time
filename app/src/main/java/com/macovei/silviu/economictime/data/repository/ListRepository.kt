package com.macovei.silviu.economictime.data.repository

import android.support.annotation.VisibleForTesting
import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.data.repository.local.ListLocalDataSource
import com.macovei.silviu.economictime.di.AppScope
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/23/18.
 */
@AppScope
class ListRepository @Inject constructor(
         private val localDataSource: ListLocalDataSource
) : ListDataSource {

    @VisibleForTesting internal var caches: MutableList<ListItem> = ArrayList()


    override fun loadList(): Flowable<List<ListItem>> {
        if (caches.size > 0) {
            // if cache is available, return it immediately
            return Flowable.just<List<ListItem>>(caches)
        } else {
            // else return data from local storage
            return localDataSource.loadList()
                    .take(1)
                    .flatMap({ Flowable.fromIterable(it) })
                    .doOnNext { listItem -> caches.add(listItem) }
                    .toList()
                    .toFlowable()
                    .filter({ list -> !list.isEmpty() })
                    .switchIfEmpty(
                            adddDummy()
                    ) // If local data is empty, fetch from remote source instead.
        }
    }

    fun adddDummy(): Flowable<List<ListItem>> {
        val listItem = ListItem(null,
                "",
                "",
                "",
                "",
                "")
        localDataSource.addListItem(listItem)
        return Flowable.just(listOf(listItem))
    }

    fun getListItem(uid: Long): Flowable<ListItem> {
        return Flowable.fromIterable(caches).filter({ listItem -> listItem.uid!! == uid })
    }

    override fun addListItem(listItem: ListItem) {
        localDataSource.addListItem(listItem)
    }

    override fun clearData() {
        caches.clear()
        localDataSource.clearData()
    }

    override fun deleteListItem(listItem: ListItem) {
        localDataSource.deleteListItem(listItem)
    }
}