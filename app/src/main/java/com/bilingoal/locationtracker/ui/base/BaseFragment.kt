package com.bilingoal.locationtracker.ui.base

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.android.support.DaggerFragment

abstract class BaseFragment<VS : BaseState, VM : BaseViewModel<VS>> : DaggerFragment() {
    abstract var viewModel: VM

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigationEvent.observe(viewLifecycleOwner, { event ->
            val consume = event.consume()
            consume?.invoke(findNavController())
        })

        val observer = Observer<VS> { state -> onStateChange(state) }
        viewModel.state.observe(viewLifecycleOwner, observer)
    }

    abstract fun onStateChange(state: VS)
}