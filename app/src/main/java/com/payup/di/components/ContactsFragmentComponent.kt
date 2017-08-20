package com.payup.di.components

import com.payup.app.ui.payment.contacts.ContactsFragment
import com.payup.di.FragmentComponent
import com.payup.di.FragmentComponentBuilder
import com.payup.di.FragmentScope
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface ContactsFragmentComponent : FragmentComponent<ContactsFragment>{
    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<ContactsFragmentComponent>
}
