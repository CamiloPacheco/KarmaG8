package com.cacomas.karmag8.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
import kotlinx.android.synthetic.main.fragment_mi_favor.view.*
import kotlinx.android.synthetic.main.item_favor.view.*
import kotlinx.android.synthetic.main.item_favor.view.CheckButton
import kotlinx.android.synthetic.main.item_favor.view.FavorNameTxt
import kotlinx.android.synthetic.main.item_favor.view.FavorStateTxt


class FavorAdapter(val favors: ArrayList<Favor>, var userName: String, var clickListener: OnItemFavorClickListener): RecyclerView.Adapter<FavorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_favor, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initialize(favors.get(position),userName, clickListener)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun initialize(item: Favor,name: String, action:OnItemFavorClickListener){

            itemView.FavorNameTxt.text= item.name
            itemView.FavorUserTxt.text = item.user
            itemView.FavorStateTxt.text= item.state
            if ( name!=item.user){

                if(item.state=="Asignado"){
                    itemView.Aceptarbutton.isVisible=false
                    if(item.realizadopor==name)
                        itemView.CheckButton.isVisible=true
                        itemView.CheckButton.setOnClickListener {
                            action.onItemClickCheck(item, adapterPosition)
                        }

                }else{

                    itemView.Aceptarbutton.setOnClickListener {
                        action.onItemClick(item, adapterPosition)
                    }

                }

            }else
                itemView.Aceptarbutton.isVisible=false


        }
    }

    interface OnItemFavorClickListener{
        fun onItemClick(item: Favor, position: Int)
        fun onItemClickCheck(item: Favor, position: Int)
    }

}