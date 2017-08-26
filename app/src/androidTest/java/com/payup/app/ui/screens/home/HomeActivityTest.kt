package com.payup.app.ui.screens.home

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.payup.R
import com.payup.app.ui.screens.test.ComponentActivityTestRule
import com.payup.di.components.activity.HomeActivityComponent
import com.payup.test.Fabricator
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HomeActivityTest : ComponentActivityTestRule.InjectionInterceptor<HomeActivity> {

    @Rule
    @JvmField
    val rule = ComponentActivityTestRule(HomeActivity::class, HomeActivityComponent.Builder::class, HomeActivityComponent::class, this)

    var testRobot = ActivityTestRobot()

    @Mock
    lateinit var viewModel: HomeViewModel

    val user = Fabricator.user()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(viewModel.user()).thenReturn(Observable.just(user))
    }

    override fun onInjectIntercept(activity: HomeActivity) {
        activity.viewModel = viewModel
    }

    @Test
    fun test_userDisplay() {
        testRobot.launch()
                .checkName(user.name)
                .checkEmail(user.email)
    }

    inner class ActivityTestRobot {
        fun launch() = apply {
            val intent = Intent(InstrumentationRegistry.getTargetContext(), HomeActivity::class.java)
            rule.launchActivity(intent)
        }

        fun checkName(name: String) = apply {
            onView(withId(R.id.avatar_name_text)).check(matches(withText(name)))
        }

        fun checkEmail(email: String) = apply {
            onView(withId(R.id.avatar_contact_text)).check(matches(withText(email)))
        }
    }
}
