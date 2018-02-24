package com.macovei.silviu.economictime.data.local

import com.macovei.silviu.economictime.data.dao.ListDao
import com.macovei.silviu.economictime.data.entity.ListItem
import com.macovei.silviu.economictime.data.repository.ListDataSource
import com.macovei.silviu.economictime.data.repository.local.ListLocalDataSource
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.then
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
class ListLocalDataSourceTest {

    private val LISTITEM1 = ListItem(1, "", "", "", "", "")
    private val LISTITEM2 = ListItem(2, "", "", "", "", "")
    private val LISTITEM3 = ListItem(3, "", "", "", "", "")
    private val THREE_LIST_ITEMS = Arrays.asList(LISTITEM1, LISTITEM2, LISTITEM3)

    @Mock lateinit var questionDao: ListDao

    private lateinit var localDataSource: ListDataSource

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        localDataSource = ListLocalDataSource(questionDao)
    }

    @Test
    fun loadList_ShouldReturnFromDatabase() {
        // Given
        val subscriber = TestSubscriber<List<ListItem>>()
        given(questionDao.all()).willReturn(Flowable.just(THREE_LIST_ITEMS))

        // When
        localDataSource.loadList().subscribe(subscriber)

        // Then
        then(questionDao).should().run { questionDao.all() }
    }

    @Test
    fun addList_ShouldInsertToDatabase() {

        // When
        localDataSource.addListItem(LISTITEM1)

        // Then
        then(questionDao).should().run { questionDao.insert(LISTITEM1) }
    }

    @Test
    fun clearData_ShouldDeleteAllDataInDatabase() {
        // Given

        // When
        localDataSource.clearData()

        // Then
        then<Any>(questionDao).should().run { questionDao.deleteAll() }
    }
}