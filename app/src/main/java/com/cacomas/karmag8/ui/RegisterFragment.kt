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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.cacomas.karmag8.R
import com.cacomas.karmag8.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {

    val firebaseAuthViewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        Log.d("MyOut","LoginFragment onViewCreated")
        
        firebaseAuthViewModel.userCreated().observe(getViewLifecycleOwner(), Observer { status ->
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

        view.findViewById<Button>(R.id.crearBtn).setOnClickListener {
            var username = view.findViewById<TextView>(R.id.userNameRegTxt).text.toString()
            var email = view.findViewById<TextView>(R.id.emailRegTxt).text.toString()
            var password = view.findViewById<TextView>(R.id.passwordRegTxt).text.toString()
            Log.d("MyOut","email="+email+" contraseña= "+password)
            firebaseAuthViewModel.signUp(email,password)
            navController.navigate(R.id.loginFragment)
        }
    }



}