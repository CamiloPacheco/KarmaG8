package com.cacomas.karmag8.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cacomas.karmag8.R
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val miFavorFragment = MiFavorFragment()
        val profileFragment = ProfileFragment()
        val favorsFragment= FavorsFragment()

        setCurrentFragment(favorsFragment)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    // Respond to navigation item 1 click
                    setCurrentFragment(profileFragment)
                    Log.d("MyOut", this.context.toString())
                    true
                }
                R.id.page_2 -> {
                    // Respond to navigation item 2 click
                    Log.d("MyOut", "Home ")
                    setCurrentFragment(favorsFragment)
                    true
                }
                R.id.page_3 -> {
                    // Respond to navigation item 2 click
                    setCurrentFragment(miFavorFragment)
                    Log.d("MyOut", "Chat ")
                    true
                }
                else -> false
            }
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        getFragmentManager()?.beginTransaction()?.apply {
            replace(R.id.fl_wrapper, fragment)
            commit()

        }

}