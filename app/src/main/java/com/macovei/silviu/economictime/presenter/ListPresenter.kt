package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.list.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@Suppress("SENSELESS_COMPARISON")
/**
 * Created by silviumacovei on 2/20/18.
 */

class ListPresenter @Inject constructor(
        private val repository: ListRepository) {

    var listView: ListView? = null

    private val disposeBag: CompositeDisposable = CompositeDisposable()


    fun attachView(view: ListView) {
        listView = view
        loadData()
    }

    fun detachView() {
        listView = null
        disposeBag.clear()
    }


    private fun loadData() {
        val disposable = repository.loadList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ this.handleReturnedData(it) }), ({ this.handleError(it) }), { listView?.stopLoadingIndicator() })
        disposeBag.add(disposable)
    }

    private fun getQuestion(uid: Long) {
        val disposable = repository.getListItem(uid)
                .filter({ listItem -> listItem != null })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listItem -> launchDetailsScreen(listItem) })
        disposeBag.add(disposable)
    }


     fun removeListItem(listItem: ListItem){
        val disposable = repository.deleteListItem(listItem)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorComplete()
                .doOnComplete { loadData() }
                .subscribe()
        disposeBag.add(disposable)
    }

    fun launchDetailsScreen(listItem: ListItem) {
        listView?.goToDetails(listItem)
    }

    fun launchEmptyDetailsScreen() {
        listView?.goToEmptyDetails()
    }

    fun processEvent(position: Long) {
        getQuestion(position)
    }


    /**
     * Updates view after loading data is completed successfully.
     */
    private fun handleReturnedData(list: List<ListItem>?) {
        listView?.stopLoadingIndicator()
        if (list != null && !list.isEmpty()) {
            listView?.showData(list)
        } else {
            listView?.showNoDataMessage()
        }
    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private fun handleError(error: Throwable) {
        listView?.stopLoadingIndicator()
        listView?.showErrorMessage(error.message)
    }


}