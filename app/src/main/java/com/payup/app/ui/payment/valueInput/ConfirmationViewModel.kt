package com.payup.app.ui.payment.valueInput

import com.payup.data.manager.SchedulerManager
import com.payup.data.repository.UserRepository
import com.payup.di.ActivityScope
import com.payup.model.Contact
import javax.inject.Inject

@ActivityScope
class ConfirmationViewModel @Inject constructor(
        private val contact: Contact,
        private val value: Double,
        private val userRepository: UserRepository,
        private val schedulerManager: SchedulerManager
) {



    fun confirmClick() {
        userRepository.sendPayment(value, contact)
                .subscribeOn(schedulerManager.workThread())
                .observeOn(schedulerManager.mainThread())
    }
}
