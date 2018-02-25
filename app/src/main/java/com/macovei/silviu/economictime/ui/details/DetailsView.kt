package com.macovei.silviu.economictime.ui.details

import com.macovei.silviu.economictime.data.entity.AdministrationItem

/**
 * Created by silviumacovei on 2/21/18.
 */
interface DetailsView {
    fun updateUiWithData(item: AdministrationItem)
    fun updateUiWithoutData()
    fun eventFinished()
    fun getDateValue(): String
    fun getProjectValue(): String
    fun getHoursValue(): Int
    fun getActivityValue(): String
    fun getStatusValue(): String
    fun changeToUpdate()
}