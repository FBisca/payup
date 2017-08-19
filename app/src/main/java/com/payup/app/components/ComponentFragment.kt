package com.payup.app.components

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.payup.di.FragmentComponentBuilder

abstract class ComponentFragment : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is ComponentFragmentActivity) {
            throw IllegalArgumentException("${javaClass.simpleName} must be attached to a ComponentActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjection()
    }

    abstract fun initInjection()

    protected inline fun <reified C: FragmentComponentBuilder<*>> injectionBuilder(): C {
        val castActivity = activity as ComponentFragmentActivity
        val builder = castActivity.fragmentInjectionFactory.get().builder(javaClass)
        return when (builder) {
            is C -> builder
            else -> throw IllegalArgumentException("Could class ComponentBuilder for ${javaClass.simpleName}")
        }

    }
}
