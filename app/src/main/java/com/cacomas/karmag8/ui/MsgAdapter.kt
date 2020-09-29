package com.cacomas.karmag8.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Msg
import kotlinx.android.synthetic.main.item_msg.view.*


class MsgAdapter (val messages: ArrayList<Msg>): RecyclerView.Adapter<MsgAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_msg, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(msg: Msg) {
            itemView.msgTextView.text =msg.user
            itemView.userTextView.text = msg.txt
        }
    }
}
