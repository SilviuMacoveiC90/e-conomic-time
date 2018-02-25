package com.macovei.silviu.economictime.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.macovei.silviu.economictime.R
import com.macovei.silviu.economictime.data.entity.AdministrationItem
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

    lateinit var adapter: ListAdapter

    @BindView(R.id.list)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.loading_indicator)
    lateinit var loadingView: LinearLayout

    @BindView(R.id.no_elements)
    lateinit var noElements: LinearLayout

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater!!.inflate(R.layout.list_view, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view!!)
        adapter = ListAdapter()
        updateFromAdapter()
        recyclerView.adapter = adapter
        listPresenter.attachView(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listPresenter.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.add) {

        }
        when (item?.itemId) {
            R.id.add -> listPresenter.launchEmptyDetailsScreen()
            R.id.delete_all -> listPresenter.removeList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateFromAdapter() {
        adapter.setItemClickListener(object : ListAdapter.ItemClickListener {
            override fun deleteElement(item: AdministrationItem) {
                listPresenter.removeListItem(item)
            }

            override fun goToDetails(uid: Long) {
                listPresenter.processEvent(uid)
            }
        })
    }

    override fun showData(list: List<AdministrationItem>) {
        recyclerView.removeAllViewsInLayout()
        adapter.replace(list)
    }

    override fun showNoDataMessage() {
        recyclerView.removeAllViewsInLayout()
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.GONE
        noElements.visibility = View.VISIBLE
        Toast.makeText(context, "The list is empty", Toast.LENGTH_LONG)
    }


    override fun goToDetails(administrationItem: AdministrationItem) {
        nav.goToEdit(administrationItem)
    }

    override fun goToEmptyDetails() {
        nav.goToEmptyEdit()
    }

    override fun startLoadingIndicator() {
        loadingView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        noElements.visibility = View.GONE
    }

    override fun stopLoadingIndicator() {
        loadingView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        noElements.visibility = View.GONE
    }
}


