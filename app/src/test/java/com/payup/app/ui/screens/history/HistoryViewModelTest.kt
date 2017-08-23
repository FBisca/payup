package com.payup.app.ui.screens.history

import com.payup.app.ui.entities.HistoryListEntity
import com.payup.app.ui.screens.history.HistoryViewModel.ViewState.ListState
import com.payup.data.manager.TestSchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.test.Fabricator
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*

class HistoryViewModelTest {

    lateinit var viewModel: HistoryViewModel

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HistoryViewModel(userRepository, TestSchedulerManager())
    }

    @Test
    fun test_historyListAfterViewCreated() {
        val list = listOf(Fabricator.transaction())

        `when`(userRepository.getTransactionHistory()).thenReturn(Single.just(list))

        val test = viewModel.viewState.test()

        viewModel.viewCreated()

        test.assertNoErrors()
        val viewState = test.values().last()
        assertThat(viewState, `is`(instanceOf(ListState::class.java)))

        val listState = viewState as ListState
        assertThat(listState.list.size, equalTo(2))
        assertThat(listState.list.first(), `is`(instanceOf(HistoryListEntity.GraphList::class.java)))
        assertThat(listState.list[1], `is`(instanceOf(HistoryListEntity.TransactionEntry::class.java)))

        val entry = listState.list[1] as HistoryListEntity.TransactionEntry
        assertThat(entry.transaction, equalTo(list.first()))
    }

    @Test
    fun test_historyEntitiesMappingIsCorrect() {
        val list = listOf(Fabricator.transaction())

        `when`(userRepository.getTransactionHistory()).thenReturn(Single.just(list))

        val test = viewModel.viewState.test()

        viewModel.viewCreated()

        test.assertNoErrors()
        val viewState = test.values().last() as ListState

        assertThat(viewState.list.size, equalTo(2))
        assertThat(viewState.list.first(), `is`(instanceOf(HistoryListEntity.GraphList::class.java)))
        assertThat(viewState.list[1], `is`(instanceOf(HistoryListEntity.TransactionEntry::class.java)))
    }

    @Test
    fun test_historyListIsSortedByDate() {
        val oldDate = Date().apply { time -= 100000000 }
        val newDate = Date()

        val list = listOf(
                Fabricator.transaction(date = oldDate),
                Fabricator.transaction(date = newDate)
        )

        `when`(userRepository.getTransactionHistory()).thenReturn(Single.just(list))

        val test = viewModel.viewState.test()

        viewModel.viewCreated()

        test.assertNoErrors()
        val viewState = test.values().last() as ListState

        val firstEntry = viewState.list[1] as HistoryListEntity.TransactionEntry
        val secondEntry = viewState.list[2] as HistoryListEntity.TransactionEntry
        assertThat(firstEntry.transaction, equalTo(list[1]))
        assertThat(secondEntry.transaction, equalTo(list[0]))
    }

    @Test
    fun test_graphListIsSortedByTotalValueAndGroupedByUser() {
        val userOne = Fabricator.contact(clientId = 1)
        val userTwo = Fabricator.contact(clientId = 2)

        val list = listOf(
                Fabricator.transaction(userTwo, 130.0),
                Fabricator.transaction(userOne, 100.0),
                Fabricator.transaction(userOne, 150.0)
        )

        `when`(userRepository.getTransactionHistory()).thenReturn(Single.just(list))

        val test = viewModel.viewState.test()

        viewModel.viewCreated()

        test.assertNoErrors()
        val viewState = test.values().last() as ListState

        val graphList = viewState.list[0] as HistoryListEntity.GraphList
        assertThat(graphList.items.size, equalTo(2))

        assertThat(graphList.items.first().user, equalTo(userOne))
        assertThat(graphList.items.first().totalValue, equalTo(250.0))

        assertThat(graphList.items[1].user, equalTo(userTwo))
        assertThat(graphList.items[1].totalValue, equalTo(130.0))
    }
}
