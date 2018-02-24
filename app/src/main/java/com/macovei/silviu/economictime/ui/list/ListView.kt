package com.macovei.silviu.economictime.ui.list

import com.macovei.silviu.economictime.data.entity.ListItem

/**
 * Created by silviumacovei on 2/20/18.
 */
interface ListView {
    fun goToDetails(listItem: ListItem)
    fun stopLoadingIndicator() {}
    fun showErrorMessage(localizedMessage: String?) {}
    fun showData(list: List<ListItem>) {}
    fun showNoDataMessage() {}
    fun goToEmptyDetails() {}
}