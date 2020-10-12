package com.cacomas.karmag8.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cacomas.karmag8.R
import com.cacomas.karmag8.util.PreferenceProvider
import com.cacomas.karmag8.viewmodel.LoginViewModel
import com.cacomas.karmag8.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


class LoginFragment : Fragment() {

    val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        Log.d("MyOut","LoginFragment onViewCreated")

        loginViewModel.userCreated().observe(getViewLifecycleOwner(), Observer { status ->
            if (status == true){
                Toast.makeText(
                    this.requireContext(), "Authentication Ok.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this.requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

        loginViewModel.logged().observe(getViewLifecycleOwner(), Observer { uid ->
            Log.d("MyOut","LoginFragment logged with "+uid)
            var aux = PreferenceProvider.getLogged()
            if (aux != ""){
                Toast.makeText(
                    this.requireContext(), "Logged Ok."+uid,
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(R.id.homeFragment)
            } else {
                /**Toast.makeText(
                    this.requireContext(), "Logged failed.",
                    Toast.LENGTH_SHORT
                ).show()*/
            }

        })

        view.findViewById<Button>(R.id.registBtn).setOnClickListener {
            navController.navigate(R.id.registerFragment)
        }
        view.findViewById<Button>(R.id.accederBtn).setOnClickListener {
            var email = view.findViewById<TextView>(R.id.emailTxt).text.toString()
            var pass = view.findViewById<TextView>(R.id.passTxt).text.toString()
            loginViewModel.signIn(email, pass)
        }
    }
}