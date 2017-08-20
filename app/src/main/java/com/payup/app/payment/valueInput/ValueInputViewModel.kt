package com.payup.app.payment.valueInput

import com.payup.app.components.Navigator
import com.payup.di.FragmentScope
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@FragmentScope
class ValueInputViewModel @Inject constructor(
        private val navigator: Navigator
) {

    val inputEvents: BehaviorSubject<String> = BehaviorSubject.createDefault("0")

    fun inputClick(code: Int) {
        when (code) {
            in 0..9 -> concatValue(code)
            -1 -> removeValue()
        }
    }

    fun sendClick() {
        navigator.goToPaymentConfirmation()
    }

    private fun removeValue() {
        val oldValue = inputEvents.value
        val newValue = if (oldValue.length > 1) {
            oldValue.substring(0, oldValue.length -1)
        } else {
            "0"
        }
        inputEvents.onNext(newValue)
    }

    private fun concatValue(code: Int) {
        val oldValue = inputEvents.value
        val newValue = when {
            oldValue == "0" -> code.toString()
            oldValue.length >= 7 -> oldValue.substring(1 until oldValue.length) + code
            else -> oldValue + code
        }
        inputEvents.onNext(newValue)
    }
}
