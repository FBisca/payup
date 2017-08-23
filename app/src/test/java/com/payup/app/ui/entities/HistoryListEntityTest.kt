package com.payup.app.ui.entities

import com.payup.test.Fabricator
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Test
import java.util.*

class HistoryListEntityTest {

    @Test
    fun test_historyEntitiesMappingIsCorrect() {
        val list = listOf(Fabricator.transaction())

        val convertedList = HistoryListEntity.convertToListEntity(list)

        assertThat(convertedList.size, equalTo(2))
        assertThat(convertedList.first(), `is`(instanceOf(HistoryListEntity.GraphList::class.java)))
        assertThat(convertedList[1], `is`(instanceOf(HistoryListEntity.TransactionEntry::class.java)))
    }

    @Test
    fun test_historyListIsSortedByDate() {
        val oldDate = Date().apply { time -= 100000000 }
        val newDate = Date()

        val list = listOf(
                Fabricator.transaction(date = oldDate),
                Fabricator.transaction(date = newDate)
        )

        val convertedList = HistoryListEntity.convertToListEntity(list)

        val firstEntry = convertedList[1] as HistoryListEntity.TransactionEntry
        val secondEntry = convertedList[2] as HistoryListEntity.TransactionEntry
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

        val convertedList = HistoryListEntity.convertToListEntity(list)

        val graphList = convertedList.first() as HistoryListEntity.GraphList
        assertThat(graphList.items.size, equalTo(2))

        assertThat(graphList.items.first().user, equalTo(userOne))
        assertThat(graphList.items.first().totalValue, equalTo(250.0))

        assertThat(graphList.items[1].user, equalTo(userTwo))
        assertThat(graphList.items[1].totalValue, equalTo(130.0))
    }

}
