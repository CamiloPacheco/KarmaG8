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
import androidx.navigation.fragment.findNavController
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.viewmodel.ProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth

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
        var nombreUsuario : String? = ""
        var emailId: String = user?.email!!

        if (user != null) {
            Log.v("MyOut", "usuario de esta vaina " + emailId)
        }

        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            for(usuarios in it){
                if(usuarios.email == emailId){
                    nombreUsuario = usuarios.username
                    Log.v("MyOut", "usuario encontrado " +nombreUsuario)
                }
            }
        })

        profileViewModel.getFavor().observe(viewLifecycleOwner, Observer {
            var contador = 0
            val arrayFavores = mutableListOf<Favor>()

            for (obj in it){
                if(obj.realizadopor.equals(nombreUsuario)   && contador<3){
                    arrayFavores.add(obj)
                    contador++
                }
            }
            if(arrayFavores.size > 0){
                view.findViewById<TextView>(R.id.nameFavor1).text = arrayFavores[0].name
                view.findViewById<TextView>(R.id.idFavor1).text = arrayFavores[0].descripcion
            }
            if(arrayFavores.size > 1){
                view.findViewById<TextView>(R.id.nameFavor2).text = arrayFavores[1].name
                view.findViewById<TextView>(R.id.idFavor2).text = arrayFavores[1].descripcion
            }
            if(arrayFavores.size == 3){
                view.findViewById<TextView>(R.id.nameFavor3).text = arrayFavores[2].name
                view.findViewById<TextView>(R.id.idFavor3).text = arrayFavores[2].descripcion
            }



        })

        profileViewModel.getKarma().observe(viewLifecycleOwner, Observer {
            for (karma in it){
                if(karma.user == nombreUsuario){
                    view.findViewById<TextView>(R.id.karmaCoins).text = karma.puntos
                }
            }
        })

        view.findViewById<FloatingActionButton>(R.id.loggoutBtn).setOnClickListener {
            profileViewModel.logOut()
            var navController = findNavController()
            navController.navigate(R.id.loginFragment)
        }

    }

}