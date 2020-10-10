package com.cacomas.karmag8.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.FavorRealizado
import com.cacomas.karmag8.viewmodel.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth

     init {
         auth = Firebase.auth

     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user = auth.currentUser

        /**val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName("Orlando")
            .build()
        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.v("MyOut", "User profile updated.")
                }
            }*/


        if (user != null) {
            Log.v("MyOut", "usuario de esta vaina " + user.displayName)
        }



        profileViewModel.getFavores().observe(viewLifecycleOwner, Observer {
            var contador = 0
            val arrayFavores = mutableListOf<FavorRealizado>()

            for (favor in it){
                if (user != null) {
                    if(favor.RealizadoPor == user.displayName && contador<3){
                        arrayFavores.add(favor)
                        contador++
                    }
                }
            }
            if(arrayFavores.size > 0){
                view.findViewById<TextView>(R.id.nameFavor1).text = arrayFavores[0].Tipo
                view.findViewById<TextView>(R.id.idFavor1).text = arrayFavores[0].User
            }
            if(arrayFavores.size > 1){
                view.findViewById<TextView>(R.id.nameFavor2).text = arrayFavores[1].Tipo
                view.findViewById<TextView>(R.id.idFavor2).text = arrayFavores[1].User
            }
            if(arrayFavores.size == 3){
                view.findViewById<TextView>(R.id.nameFavor3).text = arrayFavores[2].Tipo
                view.findViewById<TextView>(R.id.idFavor3).text = arrayFavores[2].User
            }



        })

        profileViewModel.getKarma().observe(viewLifecycleOwner, Observer {

            for (karma in it){
                if (user != null) {
                    if(karma.User == user.displayName){
                        view.findViewById<TextView>(R.id.karmaCoins).text = karma.puntos
                    }
                }
            }
        })

    }

}