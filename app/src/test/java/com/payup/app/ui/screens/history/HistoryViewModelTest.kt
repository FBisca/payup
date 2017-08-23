package com.payup.app.ui.screens.history

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

class HistoryViewModelTest {

    lateinit var viewModel: HistoryViewModel

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = HistoryViewModel(userRepository, TestSchedulerManager(), HistoryViewModel.ViewState.Loading)
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
        assertThat(listState.list.size, equalTo(1))
        assertThat(listState.list.first(), equalTo(list.first()))
    }
}
