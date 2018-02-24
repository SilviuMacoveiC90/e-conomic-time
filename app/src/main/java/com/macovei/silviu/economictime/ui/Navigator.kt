package com.macovei.silviu.economictime.ui

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.macovei.silviu.economictime.MainActivity
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.ui.details.DetailsFragment
import com.macovei.silviu.economictime.ui.list.ListFragment
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/21/18.
 */
class Navigator @Inject constructor(act: MainActivity) {


    val supportFragm: FragmentManager = act.supportFragmentManager
    val id = R.id.mainFrame

    fun goToDefault() {
        supportFragm.beginTransaction()
                .replace(id, ListFragment())
                .commit()
    }

    fun goToEdit(listItem: ListItem) {

        var fragment = DetailsFragment()
        var bundle = Bundle()
        bundle.putLong("item", listItem.uid!!)
        fragment.arguments = bundle
        supportFragm.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }

    fun goToEmptyEdit() {
        var fragment = DetailsFragment()
        supportFragm.beginTransaction()
                .replace(id, fragment)
                .addToBackStack(fragment.toString())
                .commit()
    }


}