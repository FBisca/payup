package com.payup.app.ui.screens.payment

import com.payup.app.ui.screens.payment.contacts.ContactsAdapter
import com.payup.data.repository.UserRepository
import com.payup.di.arch.ActivityScope
import com.payup.model.Contact
import com.payup.model.User
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

@ActivityScope
class PaymentViewModel @Inject constructor(
        viewState: ViewState,
        private val userRepository: UserRepository
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
        return userRepository.getUser().toObservable()
    }

    sealed class ViewState {
        object ContactSelect : ViewState()
        data class ValueInput(val contact: Contact) : ViewState()
    }
}
