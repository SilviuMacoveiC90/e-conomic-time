package com.macovei.silviu.economictime.ui.list

import com.macovei.silviu.economictime.data.model.ListItem

/**
 * Created by silviumacovei on 2/20/18.
 */
interface ListView {
    fun updateUi(items: Collection<ListItem>)
    fun goToDetails(position: Int)
}