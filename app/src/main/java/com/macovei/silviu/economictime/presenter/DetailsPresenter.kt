package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.details.DetailsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/21/18.
 */
class DetailsPresenter @Inject constructor(
        private val repository: ListRepository) {

    private var detailsView: DetailsView? = null

    private val disposeBag: CompositeDisposable = CompositeDisposable()

    fun attachView(view: DetailsView) {
        detailsView = view

    }

    fun detachView() {
        detailsView = null
        disposeBag.dispose()
    }


    fun prepareItem(uid: Long) {
        getItem(uid)
    }

    fun saveData(listItem: ListItem) {
        insertItem(listItem)
    }

    private fun getItem(uid: Long) {
        val disposable = repository.getListItem(uid)
                .filter({ true })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listItem -> detailsView?.updateUiWithData(listItem) })
        disposeBag.add(disposable)
    }


    private fun insertItem(listItem: ListItem) {
        val disposable = repository.addListItem(listItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({detailsView?.eventFinished()})
        disposeBag.add(disposable)
    }

}