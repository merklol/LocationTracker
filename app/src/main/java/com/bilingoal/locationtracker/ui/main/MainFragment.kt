package com.bilingoal.locationtracker.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bilingoal.locationtracker.R
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    private val args: MainFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAccount = args.userAccount
        greetingsView.text = "Hi, ${userAccount?.name}!"
    }
}