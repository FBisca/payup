package com.payup.app.ui.screens.history

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.payup.R
import com.payup.app.ui.entities.HistoryListEntity
import com.payup.app.ui.screens.RecyclerViewMatcher.Companion.withRecyclerView
import com.payup.app.ui.screens.test.ComponentActivityTestRule
import com.payup.di.components.HistoryActivityComponent
import com.payup.model.Transaction
import com.payup.model.TransactionGraph
import com.payup.test.Fabricator
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HistoryActivityTest {

    var testRobot = ActivityTestRobot()

    @Mock
    lateinit var viewModel: HistoryViewModel

    @Rule
    @JvmField
    val rule = ComponentActivityTestRule(
            HistoryActivity::class,
            HistoryActivityComponent.Builder::class,
            HistoryActivityComponent::class
    ) { historyActivity ->
        historyActivity.viewModel = viewModel
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test_firstItemIsAlwaysGraph() {
        val viewState = BehaviorSubject.create<HistoryViewModel.ViewState>()

        `when`(viewModel.viewState).thenReturn(viewState)

        viewState.onNext(HistoryViewModel.ViewState.ListState(mockList()))

        testRobot.launch().isGraphDisplayed()
    }

    @Test
    fun test_transactionContent() {
        val viewState = BehaviorSubject.create<HistoryViewModel.ViewState>()

        `when`(viewModel.viewState).thenReturn(viewState)

        val transaction = Fabricator.transaction(value = 100.0)

        viewState.onNext(HistoryViewModel.ViewState.ListState(mockList(transactionList = listOf(transaction))))

        testRobot.launch()
                .checkUserName(1, transaction.user.name)
                .checkPhoneNumber(1, transaction.user.phoneNumber)
                .checkValue(1, "\$100.00")
    }

    private fun mockList(
            graphList: List<TransactionGraph> = listOf(Fabricator.transactionGraph()),
            transactionList: List<Transaction> = listOf(Fabricator.transaction())
    ): List<HistoryListEntity> {

        return listOf(
                HistoryListEntity.GraphList(graphList),
                *transactionList.map { HistoryListEntity.TransactionEntry(it) }.toTypedArray()
        )
    }

    inner class ActivityTestRobot {

        fun launch() = apply {
            val intent = Intent(InstrumentationRegistry.getTargetContext(), HistoryActivity::class.java)
            rule.launchActivity(intent)
        }

        fun isGraphDisplayed() = apply {
            onView(withRecyclerView(R.id.history_list).atPositionOnView(0, R.id.list_graph))
                    .check(matches(isDisplayed()))
        }

        fun checkUserName(position: Int, name: String) = apply {
            onView(withRecyclerView(R.id.history_list).atPositionOnView(position, R.id.contact_name_text))
                    .check(matches(withText(name)))
        }

        fun checkPhoneNumber(position: Int, phoneNumber: String) = apply {
            onView(withRecyclerView(R.id.history_list).atPositionOnView(position, R.id.contact_phone_text))
                    .check(matches(withText(phoneNumber)))
        }

        fun checkValue(position: Int, value: String) = apply {
            onView(withRecyclerView(R.id.history_list).atPositionOnView(position, R.id.total_value))
                    .check(matches(withText(value)))
        }

    }
}
