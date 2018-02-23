package com.macovei.silviu.economictime.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.di.Injectable
import com.macovei.silviu.economictime.presenter.ListPresenter
import com.macovei.silviu.economictime.ui.Navigator
import com.macovei.silviu.economictime.ui.list.adapter.ListAdapter
import javax.inject.Inject


/**
 * Created by silviumacovei on 2/20/18.
 */

class ListFragment : Fragment(), ListView, Injectable {

    @Inject
    lateinit var nav: Navigator

    @Inject
    lateinit var listPresenter: ListPresenter

    @BindView(R.id.list)
    lateinit var list: RecyclerView

    lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater!!.inflate(R.layout.list_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
        adapter = ListAdapter()
        updateFromAdapter()
        list.adapter = adapter
        listPresenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listPresenter.detachView()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add) {
            listPresenter.launchDetailsScreen(-1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFromAdapter() {
        adapter.setItemClickListener(object : ListAdapter.ItemClickListener {
            override fun onItemClick(position: Int, toDelete: Boolean) {
                listPresenter.processEvent(position, toDelete)
            }
        })
    }


    override fun updateUi(items: Collection<ListItem>) {
        adapter.replace(items)
    }


    override fun goToDetails(position: Int) {
        nav.goToEdit(position)
    }
}


