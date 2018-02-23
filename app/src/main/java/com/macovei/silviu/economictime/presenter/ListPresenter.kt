package com.macovei.silviu.economictime.presenter

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.macovei.silviu.economictime.data.model.ListItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.list.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by silviumacovei on 2/20/18.
 */

class ListPresenter @Inject constructor(
        private val repository: ListRepository
) {

    var listView: ListView? = null

    private val disposeBag: CompositeDisposable = CompositeDisposable()

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onAttach() {
        loadData()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDetach() {
        // Clean up any no-longer-use resources here
        disposeBag.clear()
    }

    fun attachView(view: ListView) {
        listView = view
        loadData()
    }

    fun detachView() {
        listView = null
    }

    init {
//        loadData()
    }

    fun loadData() {
        // Load new one and populate it into view

//        if (listDao.count() != 0) {
//            listView?.updateUi(listDao.all())
//        } else {
//            listDao.insert(ListItem(null, "Monday,November 13, 2017", "Castle Construction", "Design", "2.00", "Approved")
//                    , ListItem(null, "Monday,November 13, 2017", "Api Project", "Analys", "12.00", "Not approved"))
//            loadData()
//        }
    }

    private fun loadQuestions() {
        val disposable = repository.loadList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(({ this.handleReturnedData(it) }), ({ this.handleError(it) }), { listView.stopLoadingIndicator() })
        disposeBag.add(disposable)
    }

    private fun getQuestion(questionId: Long) {
        val disposable = repository.getListItem(questionId)
                .filter({ question -> question != null })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ question -> listView.showQuestionDetail(question) })
        disposeBag.add(disposable)
    }

    fun launchDetailsScreen(int: Int) {
        listView?.goToDetails(int)
    }

    fun processEvent(position: Int, toDelete: Boolean) {
        if (toDelete) {
            listDao.delete(listDao.all()[position])
            loadData()
            return
        }
        launchDetailsScreen(position)
    }


    /**
     * Updates view after loading data is completed successfully.
     */
    private fun handleReturnedData(list: List<ListItem>?) {

    }

    /**
     * Updates view if there is an error after loading data from repository.
     */
    private fun handleError(error: Throwable) {

    }


}