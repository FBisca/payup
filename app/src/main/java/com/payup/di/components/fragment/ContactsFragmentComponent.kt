package com.payup.di.components.fragment

import com.payup.app.ui.screens.payment.contacts.ContactsFragment
import com.payup.di.arch.FragmentComponent
import com.payup.di.arch.FragmentScope
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface ContactsFragmentComponent : FragmentComponent<ContactsFragment> {
    @Subcomponent.Builder
    interface Builder : FragmentComponent.Builder<ContactsFragment>
}
