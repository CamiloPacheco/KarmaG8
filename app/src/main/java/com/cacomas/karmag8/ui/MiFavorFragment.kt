package com.cacomas.karmag8.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.viewmodel.MiFavorViewModel
import com.cacomas.karmag8.viewmodel.ProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.add_favor_form.*
import kotlinx.android.synthetic.main.fragment_mi_favor.*

class MiFavorFragment : Fragment() {

    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val favorViewModel: MiFavorViewModel by activityViewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var MyFavor:Favor
    var realizadoporUser:String=""

    init {
        auth = Firebase.auth

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_favor, container, false)
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

        favorViewModel.getFavor().observe(viewLifecycleOwner, Observer {
            val favorName= view.findViewById<TextView>(R.id.FavorNameTxt)
            val favorState= view.findViewById<TextView>(R.id.FavorStateTxt)
            val CheckButton=view.findViewById<FloatingActionButton>(R.id.CheckButton)
            if (it.isNotEmpty()){
                for(favor in it){
                    if(favor.user == nombreUsuario){
                        MyFavor=favor
                        favorName.text = favor.name
                        favorState.text ="Estado: "+favor.state
                        RealizadoporTxt.text="Siendo Reliazado por: "+favor.realizadopor
                        if (favor.state=="Asignado"){
                            CheckButton.isVisible=true
                            realizadoporUser= favor.realizadopor.toString()
                        }
                    }
                }

            }

        })

        view.findViewById<FloatingActionButton>(R.id.AddFavorButton).setOnClickListener {
            val singleItems = arrayOf("Sacar Fotocopias", "Comprar en KM5", "Buscar Domicilio en Puerta 7")
            val checkedItem = 1
            val builder = AlertDialog.Builder(this.context)
            val inflater = layoutInflater
            var editText=""
            builder.setTitle("Add Favor")
            builder.setSingleChoiceItems(singleItems, checkedItem) { dialog, which ->
            // Respond to item chosen
                editText=singleItems[checkedItem]

            }
            val dialogLayout = inflater.inflate(R.layout.add_favor_form, null)
            val editText2  = dialogLayout.findViewById<EditText>(R.id.editText2)
            val editText3  = dialogLayout.findViewById<EditText>(R.id.editText3)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { _, _ ->
                favorViewModel.setFavor(Favor(editText.toString(),"Inicial",nombreUsuario,editText2.text.toString(),"","","Pendiente ",editText3.text.toString()))
                nombreUsuario?.let { it1 -> favorViewModel.karmaPoint(it1,"0") }

                builder.context
            }
            builder.show()

        }

        view.findViewById<FloatingActionButton>(R.id.CheckButton).setOnClickListener{
            nombreUsuario?.let { it1 -> favorViewModel.endFavor(MyFavor, it1) }
        }
        view.findViewById<FloatingActionButton>(R.id.chatButton).setOnClickListener{
            val navController = findNavController()
            val bundle = Bundle()
            bundle.putString("Nombre",realizadoporUser)
            navController.navigate(R.id.chat, bundle)
        }
    }

}