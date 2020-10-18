package com.cacomas.karmag8.ui
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.model.Karma
import com.cacomas.karmag8.viewmodel.MiFavorViewModel
import com.cacomas.karmag8.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_favors.view.*


class FavorsFragment : Fragment(), FavorAdapter.OnItemFavorClickListener {
    var userName =""
    var nombreUsuario : String? = ""
    private val adapter =FavorAdapter(ArrayList(), userName, this)
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val favorViewModel: MiFavorViewModel by activityViewModels()
    private var karmas : List<Karma>? =null
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().favors_Recycler.adapter = adapter
        requireView().favors_Recycler.layoutManager = LinearLayoutManager(requireContext())
        val COUNTRIES = arrayOf("Sacar Fotocopias", "Comprar en KM5", "Buscar Domicilio","Todos")

        val adapter1 = ArrayAdapter(
            requireContext(), R.layout.dropdown_menu_popup_item,
            COUNTRIES
        )

        val editTextFilledExposedDropdown: AutoCompleteTextView =
            view.findViewById(R.id.filled_exposed_dropdown)
        editTextFilledExposedDropdown.setAdapter(adapter1)

        editTextFilledExposedDropdown.onItemSelectedListener
        var user = auth.currentUser
        var emailId: String = user?.email!!


        editTextFilledExposedDropdown.setOnItemClickListener { adapterView, view, i, l ->
            val aux = ArrayList<Favor>()
            favorViewModel.getFavor().observe(viewLifecycleOwner, Observer {
                for (favor in it){
                    if (favor.name==editTextFilledExposedDropdown.text.toString()|| editTextFilledExposedDropdown.text.toString()=="Todos" ){
                            aux.add(favor)
                    }


                }

                    adapter.favors.clear()
                    adapter.favors.addAll(aux)
                    adapter.userName = nombreUsuario.toString()
                    adapter.notifyDataSetChanged()
                
            })

        }
        favorViewModel.getFavor().observe(viewLifecycleOwner, Observer {

            adapter.favors.clear()
            adapter.favors.addAll(it)
            adapter.userName = nombreUsuario.toString()
            adapter.notifyDataSetChanged()
        })


        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            for (usuarios in it) {
                if (usuarios.email == emailId) {
                    nombreUsuario = usuarios.username
                    Log.v("MyOut", "usuario encontrado " + nombreUsuario)
                }
            }
        })

        profileViewModel.getKarma().observe(viewLifecycleOwner, Observer {
            karmas = it
        })



    }

    override fun onItemClick(item: Favor, position: Int) {
        Log.v("MyOut", "OnClickAceptarButton funcionando ")
        item.realizadopor= nombreUsuario
        favorViewModel.updateFavor(item)
    }

    override fun onItemClickCheck(item: Favor, position: Int) {
        Log.v("MyOut", "OnClickCheckButton funcionando  ")
        nombreUsuario?.let { favorViewModel.endFavor(item, it) }


    }

    override fun onItemClickDetails(item: Favor, position: Int) {

        if(item.state=="Asignado" && item.realizadopor==nombreUsuario) {
            Log.v("MyOut", "OnClickDetails funcionando  ")
            val builder = AlertDialog.Builder(this.context)
            val inflater = layoutInflater
            builder.setTitle(item.name)
            builder.setMessage(
                "state :" + item.state + "\n" +
                        "Description:" + item.descripcion + "\n" +
                        "Adress: " + item.adress + "\n" +
                        "Created by :" + item.user + "\n" +
                        "Acepted by : " + item.realizadopor + "\n"


            )
            builder.setPositiveButton("OK") { _, _ ->
                builder.context
            }
            builder.setNegativeButton("Chat") { _, _ ->
                val navController = findNavController()
                val bundle = Bundle()
                bundle.putString("Nombre", item.user)
                navController.navigate(R.id.chat, bundle)
                builder.context
            }
            builder.show()
        }
    }
}