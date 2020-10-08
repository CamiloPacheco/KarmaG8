package com.cacomas.karmag8.ui

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_chat.view.*

class ChatFragment : Fragment() {
    private val adapter =MsgAdapter(ArrayList())
    private val msgViewModel: MsgViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().message_Recycler.adapter = adapter
        requireView().message_Recycler.layoutManager = LinearLayoutManager(requireContext())

        msgViewModel.getMsg()

       msgViewModel.getMsg().observe(viewLifecycleOwner, Observer {
           adapter.messages.clear()
           adapter.messages.addAll(it)
           adapter.notifyDataSetChanged()
       })

        view.findViewById<Button>(R.id.sendButton).setOnClickListener {
           var txt :String = view.findViewById<TextView>(R.id.messageEditText).text.toString()
            msgViewModel.setMsg(Msg("Camilo",txt))
        }
    }

}