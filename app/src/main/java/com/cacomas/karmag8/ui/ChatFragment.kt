package com.cacomas.karmag8.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Msg
import com.cacomas.karmag8.viewmodel.MsgViewModel
import com.cacomas.karmag8.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_chat.view.*
import kotlinx.android.synthetic.main.fragment_favors.view.*

class ChatFragment : Fragment() {
    private var nombreUsuario : String? = ""
    private val adapter =MsgAdapter(ArrayList())
    private val msgViewModel: MsgViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user1=arguments?.getString("Nombre")
        super.onViewCreated(view, savedInstanceState)
        requireView().message_Recycler.adapter = adapter
        requireView().message_Recycler.layoutManager = LinearLayoutManager(requireContext())
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

       msgViewModel.getMsg().observe(viewLifecycleOwner, Observer {
           val msgAux = ArrayList<Msg>()
           for (item in it) {
              if ((nombreUsuario==item.from && user1==item.to) || (nombreUsuario==item.to && user1==item.from)){
                   msgAux.add(item)
              }
           }
           adapter.messages.clear()
           adapter.messages.addAll(msgAux)
           adapter.notifyDataSetChanged()
       })

        view.findViewById<Button>(R.id.sendButton).setOnClickListener {
           var txt :String = view.findViewById<TextView>(R.id.messageEditText).text.toString()

            msgViewModel.setMsg(Msg(txt,nombreUsuario,user1))
        }
    }

}