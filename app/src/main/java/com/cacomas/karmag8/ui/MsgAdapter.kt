package com.cacomas.karmag8.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Msg
import kotlinx.android.synthetic.main.item_msg.view.*


class MsgAdapter (val messages: ArrayList<Msg>,val msgSW :ArrayList<String>): RecyclerView.Adapter<MsgAdapter.ViewHolder>() {

    var cont:Int=0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.item_msg, parent, false)
        if (msgSW[cont]=="0"){
                view =  LayoutInflater.from(parent.context).inflate(R.layout.item_msg, parent, false)
                cont++
        }
        else {
                view =  LayoutInflater.from(parent.context).inflate(R.layout.item_msg2, parent, false)
                cont++
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(messages[position])

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun initialize(item: Msg){
            itemView.txtTextView.text=item.txt

        }
    }
}
