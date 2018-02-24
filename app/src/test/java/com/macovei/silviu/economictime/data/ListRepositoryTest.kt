package com.macovei.silviu.economictime.data

import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.data.repository.ListDataSource
import com.macovei.silviu.economictime.data.repository.ListRepository
import com.macovei.silviu.economictime.data.repository.Local
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.then
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

/**
 * Created by silviumacovei on 2/24/18.
 */
class ListRepositoryTest {


    private val LISTITEM1 = ListItem(1, "", "", "", "", "")
    private val LISTITEM2 = ListItem(2, "", "", "", "", "")
    private val LISTITEM3 = ListItem(3, "", "", "", "", "")
    private val THREE_LIST_ITEMS = Arrays.asList(LISTITEM1, LISTITEM2, LISTITEM3)


    @Mock
    @Local lateinit var localDataSource: ListDataSource


    private var repository: ListRepository? = null

    private var questionsTestSubscriber: TestSubscriber<List<ListItem>>? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        repository = ListRepository(localDataSource)

        questionsTestSubscriber = TestSubscriber<List<ListItem>>()
    }


    @Test
    fun loadList_ShouldReturnFromLocal_IfCacheIsNotAvailable() {
        // Given
        // No cache
        doReturn(Flowable.just<List<ListItem>>(THREE_LIST_ITEMS)).`when`(localDataSource).loadList()

        // When
        repository!!.loadList().subscribe(questionsTestSubscriber)

        // Then
        // Loads from local storage
        verify<Any>(localDataSource).run { localDataSource.loadList() }

        questionsTestSubscriber!!.assertValue(THREE_LIST_ITEMS)
    }


    @Test
    fun refreshData_ShouldClearOldDataFromLocal() {
        // Given
        given(localDataSource!!.loadList()).willReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        repository!!.loadList().subscribe(questionsTestSubscriber)

        // Then
        then<Any>(localDataSource).should().run { localDataSource.clearData() }
    }


    @Test
    fun refreshData_ShouldAddNewDataToLocal() {
        // Given
        given(localDataSource!!.loadList()).willReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        repository!!.loadList().subscribe(questionsTestSubscriber)

        // Then
        then<Any>(localDataSource).should().run { localDataSource.addListItem(LISTITEM1) }
        then<Any>(localDataSource).should().run { localDataSource.addListItem(LISTITEM2) }
        then<Any>(localDataSource).should().run { localDataSource.addListItem(LISTITEM3) }
    }


    @Test(expected = UnsupportedOperationException::class)
    fun addListItem_ShouldThrowException() {
        repository!!.addListItem(LISTITEM1)
    }
}