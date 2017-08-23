package com.payup.test

import com.payup.model.Contact
import com.payup.model.Transaction
import com.payup.model.TransactionGraph
import com.payup.model.User
import java.util.*

object Fabricator {

    fun contact(
            clientId: Int = 1,
            name: String = "Regina Cazé",
            phoneNumber: String = "(11) 98378-0993",
            imageUrl: String = "http://www.google.com/logo"
    ) = Contact(
            clientId,
            name,
            phoneNumber,
            imageUrl
    )

    fun user(
            name: String = "José da Nica",
            email: String = "johnofnike@gmail.com"
    ) = User(
            name,
            email
    )

    fun transaction(
            user: Contact = contact(),
            value: Double = 100.0,
            date: Date = Date()
    ) = Transaction(
            user,
            value,
            date
    )

    fun transactionGraph(
            user: Contact = contact(),
            totalValue: Double = 1000.0,
            percentage: Int = 45
    ) = TransactionGraph(
            user,
            totalValue,
            percentage
    )
}
