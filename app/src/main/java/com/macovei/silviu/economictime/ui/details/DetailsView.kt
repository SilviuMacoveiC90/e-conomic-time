package com.macovei.silviu.economictime.ui.details

import com.macovei.silviu.economictime.data.entity.ListItem

/**
 * Created by silviumacovei on 2/21/18.
 */
interface DetailsView {
    fun updateUiWithData(item: ListItem)
    fun updateUiWithoutData()
    fun eventFinished()
}