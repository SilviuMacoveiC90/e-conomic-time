package com.macovei.silviu.economictime.data.repository

import com.macovei.silviu.economictime.data.entity.AdministrationItem
import io.reactivex.Flowable

/**
 * Created by silviumacovei on 2/24/18.
 */
interface IListRepo : ListDataSource {
    fun getListItem(uid: Long): Flowable<AdministrationItem>
}