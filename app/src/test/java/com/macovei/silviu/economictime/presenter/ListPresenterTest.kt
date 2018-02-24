package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.list.ListView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runners.Parameterized
import org.mockito.BDDMockito.then
import org.mockito.Mockito.atLeastOnce
import java.util.*
import java.util.Collections.emptyList
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit


/**
 * Created by silviumacovei on 2/24/18.
 */
class ListPresenterTest {

    private val LISTITEM1 = ListItem(1, "", "", "", "", "")
    private val LISTITEM2 = ListItem(2, "", "", "", "", "")
    private val LISTITEM3 = ListItem(3, "", "", "", "", "")
    private val NO_LISTITEM = emptyList<ListItem>()
    private val THREE_LIST_ITEMS = Arrays.asList(LISTITEM1, LISTITEM2, LISTITEM3)

    @Parameterized.Parameters
    fun data(): Array<Any> {
        return arrayOf(NO_LISTITEM, THREE_LIST_ITEMS)
    }

    private lateinit var presenter: ListPresenter
    private val repo: ListRepository = mock()
    private val view: ListView = mock()

    private val immediate = object : Scheduler() {
        override fun scheduleDirect(run: Runnable,
                                    delay: Long, unit: TimeUnit): Disposable {
            return super.scheduleDirect(run, 0, unit)
        }

        override fun createWorker(): Scheduler.Worker {
            return ExecutorScheduler.ExecutorWorker(
                    Executor { it.run() })
        }
    }

    @Before
    fun setUp() {
//        RxJavaPlugins.setInitIoSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setInitComputationSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setInitNewThreadSchedulerHandler { Schedulers.trampoline() }
//        RxJavaPlugins.setInitSingleSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        presenter = ListPresenter(repo)
        presenter.listView = view

    }

    @After
    fun tearDown() {
    }


    @Test
    fun loadData_ShouldAlwaysStopLoadingIndicatorOnView_WhenComplete() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        presenter.loadData()

        // Then
        then<Any>(view).should(atLeastOnce()).run { view.stopLoadingIndicator() }
    }


    @Test
    fun loadData_ShouldShowDataOnView_WithDataReturned() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        presenter.loadData()

        // Then
        then<Any>(view).should(atLeastOnce()).run { presenter.handleReturnedData(THREE_LIST_ITEMS) }
        then<Any>(view).should(atLeastOnce()).run { view.stopLoadingIndicator() }
        then<Any>(view).should(atLeastOnce()).run { view.showData(THREE_LIST_ITEMS) }
    }


    @Test
    fun loadData_ShouldShowMessage_WhenNoDataReturned() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(NO_LISTITEM))

        // When
        presenter.loadData()

        // Then
        then<Any>(view).should(atLeastOnce()).run { view.stopLoadingIndicator() }
        then<Any>(view).should(atLeastOnce()).run { presenter.handleReturnedData(THREE_LIST_ITEMS) }
        then<Any>(view).should(never()).run { view.showData(THREE_LIST_ITEMS) }
        then<Any>(view).should(atLeastOnce()).run { view.showNoDataMessage() }
        then<Any>(view).should(atLeastOnce()).run { presenter.handleError("test") }
    }

    @Test
    fun launchDetailsScreen() {
        // Given

        // When


        // Then

    }

    @Test
    fun removeListItem() {
        // Given
        val complete = Completable.complete()
        whenever(repo.deleteListItem(1L)).thenReturn(complete)
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))
        // When
        presenter.removeListItem(LISTITEM1)
        // Then
        then<Any>(view).should(atLeastOnce()).run { view.stopLoadingIndicator() }
        verify(view).showData(THREE_LIST_ITEMS)
    }

    @Test
    fun launchEmptyDetailsScreen() {
    }

    @Test
    fun processEvent() {
    }

}