package com.payup.di.components

import com.payup.app.ui.screens.payment.valueInput.ValueInputFragment
import com.payup.di.FragmentComponent
import com.payup.di.FragmentComponentBuilder
import com.payup.di.FragmentScope
import com.payup.model.Contact
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(ValueInputFragmentModule::class))
@FragmentScope
interface ValueInputFragmentComponent : FragmentComponent<ValueInputFragment> {
    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<ValueInputFragmentComponent> {
        fun module(module: ValueInputFragmentModule): Builder
    }
}

@Module
@FragmentScope
class ValueInputFragmentModule(private val contact: Contact) {
    @Provides
    fun providesContact() = contact
}
