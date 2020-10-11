package com.cacomas.karmag8.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
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
    private val favorViewModel: MiFavorViewModel by activityViewModels()
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
        return inflater.inflate(R.layout.fragment_favors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireView().favors_Recycler.adapter = adapter
        requireView().favors_Recycler.layoutManager = LinearLayoutManager(requireContext())

        var user = auth.currentUser

        var emailId: String = user?.email!!

        profileViewModel.getUser().observe(viewLifecycleOwner, Observer {
            for(usuarios in it){
                if(usuarios.email == emailId){
                    nombreUsuario = usuarios.username
                    Log.v("MyOut", "usuario encontrado " +nombreUsuario)
                }
            }
        })
        favorViewModel.getFavor().observe(viewLifecycleOwner, Observer {
            adapter.favors.clear()
            adapter.favors.addAll(it)
            adapter.userName= nombreUsuario.toString()
            adapter.notifyDataSetChanged()
        })

    }

    override fun onItemClick(item: Favor, position: Int) {
        Log.v("MyOut", "OnClickAceptarButton funcionando " )
        item.realizadopor= nombreUsuario
        favorViewModel.updateFavor(item)
    }

    override fun onItemClickCheck(item: Favor, position: Int) {
        Log.v("MyOut", "OnClickCheckButton funcionando  " )
    }
}