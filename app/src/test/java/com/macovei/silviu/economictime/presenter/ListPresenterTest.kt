package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.entity.AdministrationItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.list.ListView
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Collections.emptyList


/**
 * Created by silviumacovei on 2/24/18.
 */
class ListPresenterTest {

    private val LISTITEM1 = AdministrationItem(1, "", "", "", 0, "")
    private val LISTITEM2 = AdministrationItem(2, "", "", "", 0, "")
    private val LISTITEM3 = AdministrationItem(3, "", "", "", 0, "")
    private val NO_LISTITEM = emptyList<AdministrationItem>()
    private val THREE_LIST_ITEMS = Arrays.asList(LISTITEM1, LISTITEM2, LISTITEM3)


    private lateinit var presenter: ListPresenter
    private var repo: ListRepository = mock()
    private var view: ListView = mock()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        repo = mock()
        view = mock()
        presenter = ListPresenter(repo)
        presenter.listView = view

    }

    @After
    fun tearDown() {
        presenter.listView = null

        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }


    @Test
    fun loadData_ShouldAlwaysStopLoadingIndicatorOnView_WhenComplete() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        presenter.loadData()

        // Then
        Thread.sleep(100)
        verify(view, times(2)).stopLoadingIndicator()
    }


    @Test
    fun loadData_ShouldShowDataOnView_WithDataReturned() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        presenter.loadData()

        // Then
        Thread.sleep(100)
        verify(view, times(2)).stopLoadingIndicator()
        verify(view, atLeastOnce()).showData(THREE_LIST_ITEMS)
    }


    @Test
    fun loadData_ShouldShowMessage_WhenNoDataReturned() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(NO_LISTITEM))

        // When
        presenter.loadData()

        // Then
        Thread.sleep(100)
        verify(view, atLeastOnce()).stopLoadingIndicator()
        verify(view, atLeastOnce()).showNoDataMessage()
    }

    @Test
    fun launchDetailsScreen() {
        // Given
        whenever(repo.loadList()).thenReturn(Flowable.just(THREE_LIST_ITEMS))
        // When
        presenter.launchDetailsScreen(LISTITEM1)

        // Then
        Thread.sleep(100)
        verify(view, atLeastOnce()).goToDetails(any())
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
        Thread.sleep(100)
        verify(view).showData(any())
    }

    @Test
    fun processEvent() {
        // Given
        whenever(repo.getListItem(1)).thenReturn(Flowable.just(LISTITEM1))

        // When
        presenter.processEvent(1)

        // Then
        Thread.sleep(100)
        verify(view).goToDetails(LISTITEM1)
    }

}