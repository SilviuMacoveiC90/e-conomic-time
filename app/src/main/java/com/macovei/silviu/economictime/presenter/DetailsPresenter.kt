package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.entity.AdministrationItem
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

    var detailsView: DetailsView? = null

    var item: AdministrationItem = AdministrationItem()

    private val disposeBag: CompositeDisposable = CompositeDisposable()

    fun attachView(view: DetailsView) {
        detailsView = view
    }

    fun detachView() {
        detailsView = null
        disposeBag.dispose()
    }

    fun prepareItem(id: Long) {
        if (id >= 0) {
            getItem(id)
        }
    }

    fun saveData() {
        detailsView?.run {
            AdministrationItem().apply {
                date = getDateValue()
                project = getProjectValue()
                activity = getActivityValue()
                hours = getHoursValue()
                status = getStatusValue()
            }.takeIf {
                isItemValid(it)
            }?.let {
                insertItem(it)
            }
        }
    }

    private fun getItem(uid: Long) {
        val disposable = repository.getListItem(uid)
                .filter({ true })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listItem ->
                    item = listItem
                    detailsView?.updateUiWithData(listItem)
                    detailsView?.changeToUpdate()
                })
        disposeBag.add(disposable)
    }


    fun insertItem(administrationItem: AdministrationItem) {
        val disposable = repository.addListItem(administrationItem)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete { detailsView?.eventFinished() }
                .subscribe()
        disposeBag.add(disposable)
    }

    fun updateItem() {
        detailsView?.run {
            item.apply {
                date = getDateValue()
                project = getProjectValue()
                activity = getActivityValue()
                hours = getHoursValue()
                status = getStatusValue()
            }
            val disposable = repository.updateItem(item)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete { detailsView?.eventFinished() }
                    .subscribe()
            disposeBag.add(disposable)
        }
    }

    fun isItemValid(item: AdministrationItem): Boolean {
        return item.run {
            activity.isNotBlank() &&
                    date.isNotBlank() &&
                    project.isNotBlank() &&
                    status.isNotBlank() &&
                    hours > 0
        }
    }

}