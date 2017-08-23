package com.payup.app.ui.screens.home

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.payup.R
import com.payup.app.App
import com.payup.app.ui.screens.anyNonNull
import com.payup.di.components.HomeActivityComponent
import com.payup.di.injectionFactory.ActivityInjectionFactory
import com.payup.model.User
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    var testRobot = HomeActivityTestRobot()

    @Mock
    lateinit var viewModel: HomeViewModel

    @Mock
    lateinit var componentBuilder: HomeActivityComponent.Builder

    @Mock
    lateinit var component: HomeActivityComponent

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        val app = InstrumentationRegistry.getTargetContext().applicationContext as App

        app.activityInjectionFactory = ActivityInjectionFactory(mapOf(HomeActivity::class.java to componentBuilder))

        `when`(componentBuilder.module(anyNonNull())).thenReturn(componentBuilder)
        `when`(componentBuilder.build()).thenReturn(component)

        `when`(component.injectMembers(anyNonNull())).then { invocation ->
            val activity: HomeActivity = invocation.getArgument(0)
            activity.apply { viewModel = this@HomeActivityTest.viewModel }
        }

        `when`(viewModel.user()).thenReturn(Observable.just(User("Dodie", "dodie@gmail.com")))
    }

    @Test
    fun test_userDisplay() {
        rule.launchActivity(Intent(InstrumentationRegistry.getTargetContext(), HomeActivity::class.java))

        testRobot.checkName("Dodie")
                .checkEmail("dodie@gmail.com")
    }

    class HomeActivityTestRobot {
        fun checkName(name: String) = apply {
            onView(withId(R.id.avatar_name_text)).check(matches(withText(name)))
        }

        fun checkEmail(email: String) = apply {
            onView(withId(R.id.avatar_contact_text)).check(matches(withText(email)))
        }
    }
}
