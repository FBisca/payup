package com.payup.di.components.fragment

import com.payup.app.ui.screens.payment.valueInput.ValueInputFragment
import com.payup.di.arch.FragmentComponent
import com.payup.di.arch.FragmentScope
import com.payup.model.Contact
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ValueInputFragmentModule::class))
@FragmentScope
interface ValueInputFragmentComponent : FragmentComponent<ValueInputFragment> {
    @Subcomponent.Builder
    interface Builder : FragmentComponent.Builder<ValueInputFragment> {
        fun module(module: ValueInputFragmentModule): Builder
    }
}

@Module
@FragmentScope
class ValueInputFragmentModule(private val contact: Contact) {
    @Provides
    fun providesContact() = contact
}
