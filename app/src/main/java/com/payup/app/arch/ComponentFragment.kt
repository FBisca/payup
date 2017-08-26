package com.payup.app.arch

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.payup.di.arch.FragmentComponent

abstract class ComponentFragment : Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        checkHierarchy(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(this, savedInstanceState)
    }

    open protected fun <B : FragmentComponent.Builder<T>, T : Fragment> injectMembers(instance: T, builder: B, savedInstanceState: Bundle?) {
        builder.build().injectMembers(instance)
    }

    open protected fun getComponentBuilder(): FragmentComponent.Builder<out Fragment> {
        val castActivity = activity as ComponentActivity<*>
        return castActivity.fragmentInjectionFactory.get().builder(javaClass)
    }

    protected inline fun <reified B: FragmentComponent.Builder<F>, F: Fragment> inject(instance: F, savedInstanceState: Bundle?) {
        val builder = getComponentBuilder() as? B ?: throw IllegalArgumentException("Could not cast component builder")
        injectMembers(instance, builder, savedInstanceState)
    }

    private fun checkHierarchy(context: Context?) {
        if (context !is ComponentActivity<*>) {
            throw IllegalArgumentException("${javaClass.simpleName} must be attached to a ComponentActivity")
        }
    }
}
