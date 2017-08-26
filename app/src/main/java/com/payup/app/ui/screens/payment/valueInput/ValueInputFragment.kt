package com.payup.app.ui.screens.payment.valueInput

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.payup.R
import com.payup.app.arch.ComponentFragment
import com.payup.databinding.FragmentValueInputBinding
import com.payup.di.arch.FragmentComponent
import com.payup.di.components.fragment.ValueInputFragmentComponent
import com.payup.di.components.fragment.ValueInputFragmentModule
import com.payup.model.Contact
import javax.inject.Inject

class ValueInputFragment : ComponentFragment() {

    companion object {
        const val ARG_CONTACT = "arg_contact"

        fun newInstance(contact: Contact): ValueInputFragment {
            val bundle = Bundle().apply { putParcelable(ValueInputFragment.ARG_CONTACT, contact) }
            return ValueInputFragment().apply { arguments = bundle }
        }
    }

    @Inject
    lateinit var viewModel: ValueInputViewModel

    private lateinit var binding: FragmentValueInputBinding

    override fun <B : FragmentComponent.Builder<T>, T : Fragment> injectMembers(instance: T, builder: B, savedInstanceState: Bundle?) {
        val castedBuilder = builder as ValueInputFragmentComponent.Builder
        castedBuilder.module(ValueInputFragmentModule(arguments.getParcelable(ARG_CONTACT)))
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
            binding.value = it
        }
    }
}
