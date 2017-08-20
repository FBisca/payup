package com.payup.app.ui.payment.valueInput

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.app.components.ComponentFragment
import com.payup.databinding.FragmentValueInputBinding
import com.payup.di.components.ValueInputFragmentComponent
import com.payup.utils.locale
import java.text.NumberFormat
import javax.inject.Inject

class ValueInputFragment : ComponentFragment() {

    @Inject
    lateinit var viewModel: ValueInputViewModel

    private lateinit var binding: FragmentValueInputBinding

    override fun initInjection(savedInstanceState: Bundle?) {
        injectionBuilder<ValueInputFragmentComponent.Builder>()
                .build()
                .injectMembers(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_value_input, container, false)
        binding.viewModel = viewModel
        binding.inputValueText.showSoftInputOnFocus = false
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.inputEvents.map { it.toDouble() / 100 }.subscribe {
            binding.inputValueText.setText(NumberFormat.getCurrencyInstance(context.locale()).format(it))
        }
    }
}
