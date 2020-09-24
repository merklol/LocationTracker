package com.bilingoal.locationtracker.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VS: BaseState, VM : BaseViewModel<VS>, H: Host>
    : DaggerFragment(), BaseView {
    abstract var viewModel: VM
    abstract val reducer: Reducer
    protected var fragmentHost: H? = null

    private val hostClassName =
        ((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>).canonicalName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationEvent.observe(viewLifecycleOwner, { event ->
            val consume = event.consume()
            consume?.invoke(findNavController())
        })

        val observer = Observer<VS> { state -> onStateChange(state) }
        viewModel.state.observe(viewLifecycleOwner, observer)
    }

    private fun onStateChange(state: VS) {
        state.accept(reducer)
    }

    protected fun hasHost(): Boolean = fragmentHost != null

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            fragmentHost = context as H
        } catch (e: Throwable) {
            throw RuntimeException("$hostClassName must be implemented to attach ${javaClass.simpleName}", e)
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentHost = null
    }
}