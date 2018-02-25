package com.macovei.silviu.economictime.ui.list

import com.macovei.silviu.economictime.data.entity.AdministrationItem

/**
 * Created by silviumacovei on 2/20/18.
 */
interface ListView {
    fun goToDetails(administrationItem: AdministrationItem)
    fun showErrorMessage(localizedMessage: String?) {}
    fun showData(list: List<AdministrationItem>) {}
    fun showNoDataMessage() {}
    fun goToEmptyDetails() {}
    fun stopLoadingIndicator() {}
    fun startLoadingIndicator() {}
}