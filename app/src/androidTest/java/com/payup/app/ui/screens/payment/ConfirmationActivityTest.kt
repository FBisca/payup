package com.payup.app.ui.screens.payment

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.payup.R
import com.payup.app.Navigator
import com.payup.app.ui.screens.test.ComponentActivityTestRule
import com.payup.di.components.activity.ConfirmationActivityComponent
import com.payup.model.Contact
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
class ConfirmationActivityTest : ComponentActivityTestRule.InjectionInterceptor<ConfirmationActivity> {

    @Rule
    @JvmField
    val rule = ComponentActivityTestRule(ConfirmationActivity::class, ConfirmationActivityComponent.Builder::class, ConfirmationActivityComponent::class, this)

    var testRobot = ActivityTestRobot()

    @Mock
    lateinit var viewModel: ConfirmationViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    override fun onInjectIntercept(activity: ConfirmationActivity) {
        activity.viewModel = viewModel
    }

    @Test
    fun test_informationDisplay() {
        val contact = Fabricator.contact()

        `when`(viewModel.viewState).thenReturn(BehaviorSubject.create())
        `when`(viewModel.contact).thenReturn(contact)
        `when`(viewModel.value).thenReturn(100.0)

        testRobot.launch(contact, 100.0)
                .checkRecipientName(contact.name)
                .checkRecipientPhone(contact.phoneNumber)
                .checkValue("$100.00")
    }

    inner class ActivityTestRobot {

        fun launch(contact: Contact, value: Double) = apply {
            val intent = Intent(InstrumentationRegistry.getTargetContext(), ConfirmationActivity::class.java)
            intent.putExtra(Navigator.EXTRA_CONTACT, contact)
            intent.putExtra(Navigator.EXTRA_VALUE, value)
            rule.launchActivity(intent)
        }

        fun checkRecipientName(name: String) = apply {
            onView(withId(R.id.recipient_name_text)).check(matches(withText(name)))
        }

        fun checkRecipientPhone(phone: String) = apply {
            onView(withId(R.id.recipient_phone_text)).check(matches(withText(phone)))
        }

        fun checkValue(value: String) = apply {
            onView(withId(R.id.value_text)).check(matches(withText(value)))
        }
    }
}
