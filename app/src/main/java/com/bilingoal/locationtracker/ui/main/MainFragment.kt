package com.bilingoal.locationtracker.ui.main

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bilingoal.locationtracker.R
import com.bilingoal.locationtracker.models.network.UserAuthenticator
import com.bilingoal.locationtracker.models.preferences.UserPreferences
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {
    private val args: MainFragmentArgs by navArgs()
    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userAccount = args.userAccount
        greetingsView.text = "Hi, ${userAccount?.name}!"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.signOutMenuOption -> {
                signOut()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        UserAuthenticator.signOut()
        userPreferences.clearUserCredentials()
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
    }
}