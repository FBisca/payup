package com.payup.di.components

import com.payup.app.payment.valueInput.ValueInputFragment
import com.payup.di.FragmentComponent
import com.payup.di.FragmentComponentBuilder
import com.payup.di.FragmentScope
import dagger.Subcomponent

@Subcomponent
@FragmentScope
interface ValueInputFragmentComponent : FragmentComponent<ValueInputFragment> {
    @Subcomponent.Builder
    interface Builder : FragmentComponentBuilder<ValueInputFragmentComponent>
}
