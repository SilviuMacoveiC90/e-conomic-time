package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.ui.details.DetailsView
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/21/18.
 */
class DetailsPresenter @Inject constructor(private val listDao: ListDao) {

    private var detailsView: DetailsView? = null

    fun attachView(view: DetailsView) {
        detailsView = view

    }

    fun detachView() {
        detailsView = null
    }


    fun prepareItem(position: Int) {
        if (position == -1) {
            detailsView?.updateUiWithoutData()
            return
        }
        var items: Collection<ListItem> = listDao.all()
        detailsView?.updateUiWithData(items.elementAt(position))
    }

    fun saveData(listItem: ListItem) {
        listDao.insert(listItem)
    }
}