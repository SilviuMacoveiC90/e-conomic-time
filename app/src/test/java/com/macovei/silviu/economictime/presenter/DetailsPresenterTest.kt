package com.macovei.silviu.economictime.presenter

import com.macovei.silviu.economictime.data.entity.AdministrationItem
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.ui.details.DetailsView
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by silviumacovei on 2/25/18.
 */
class DetailsPresenterTest {
    private val LISTITEM1 = AdministrationItem(1, "", "", "", 0, "")
    private val ITEM_ID: Long = 1


    private lateinit var presenter: DetailsPresenter
    private var repo: ListRepository = mock()
    private var view: DetailsView = mock()


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        repo = mock()
        view = mock()
        presenter = DetailsPresenter(repo)
        presenter.detailsView = view
    }

    @After
    fun tearDown() {
    }


    @Test
    fun loadItem_ShouldDisplayData_WithData() {
        // Given
        whenever(repo.getListItem(ITEM_ID)).thenReturn(Flowable.just(LISTITEM1))
        // When
        presenter.prepareItem(ITEM_ID)

        // Then
        Thread.sleep(100)
        verify(view).updateUiWithData(LISTITEM1)
        verify(view).changeToUpdate()


    }

    @Test
    fun loadItem_ShouldNotDisplayData() {
        // Given
        whenever(repo.getListItem(ITEM_ID)).thenReturn(Flowable.just(LISTITEM1))

        // When
        presenter.prepareItem(-1)

        // Then
        Thread.sleep(100)
        verify(view, never()).updateUiWithData(LISTITEM1)
        verify(view, never()).changeToUpdate()
    }


    @Test
    fun item_ShouldInsert_WithData() {
        // Given
        val complete = Completable.complete()
        whenever(repo.addListItem(LISTITEM1)).thenReturn(complete)

        // When
        presenter.insertItem(LISTITEM1)

        // Then
        Thread.sleep(100)
        verify(view).eventFinished()
    }


}