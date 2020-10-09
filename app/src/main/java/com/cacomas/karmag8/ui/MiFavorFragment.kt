package com.cacomas.karmag8.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.cacomas.karmag8.R
import com.cacomas.karmag8.model.Favor
import com.cacomas.karmag8.viewmodel.MiFavorViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.add_favor_form.*

class MiFavorFragment : Fragment() {
    private val favorViewModel: MiFavorViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_favor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favorViewModel.getFavor().observe(viewLifecycleOwner, Observer {
            val favorName= view.findViewById<TextView>(R.id.FavorNameTxt)
            val favorState= view.findViewById<TextView>(R.id.FavorStateTxt)
            favorName.text = it[0].name
            favorState.text = it[0].state
        })

        view.findViewById<FloatingActionButton>(R.id.AddFavorButton).setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val inflater = layoutInflater
            builder.setTitle("Add Favor")
            val dialogLayout = inflater.inflate(R.layout.add_favor_form, null)
            val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
            val editText2  = dialogLayout.findViewById<EditText>(R.id.editText2)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { _, _ ->
                Toast.makeText(this.context, "EditText is " + editText.text.toString(), Toast.LENGTH_SHORT).show();
                favorViewModel.setFavor(Favor(editText.text.toString(),editText2.text.toString()))
            }
            builder.show()
        }
    }

}