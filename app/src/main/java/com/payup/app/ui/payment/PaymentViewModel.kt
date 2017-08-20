package com.payup.app.ui.payment

import com.payup.app.ui.payment.contacts.ContactsAdapter
import com.payup.di.ActivityScope
import com.payup.model.Contact
import com.payup.model.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@ActivityScope
class PaymentViewModel @Inject constructor(
        viewState: ViewState
) : ContactsAdapter.Listener {

    val viewState: BehaviorSubject<ViewState> = BehaviorSubject.createDefault(viewState)

    override fun onContactClicked(contact: Contact) {
        if (viewState.value is ViewState.ContactSelect) {
            viewState.onNext(ViewState.ValueInput(contact))
        }
    }

    fun onBackPressed(): Boolean {
        if (viewState.value is ViewState.ValueInput) {
            viewState.onNext(ViewState.ContactSelect)
            return true
        }

        return false
    }

    fun user(): Observable<User> {
        return Observable.just(User("Dodie Clark", "doddleoddle@gmail.com"))
    }

    sealed class ViewState {
        object ContactSelect : ViewState()
        data class ValueInput(val contact: Contact) : ViewState()
    }
}
