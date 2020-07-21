package com.example.itkotobadictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val mainframe = inflater.inflate(R.layout.search_fragment, container, false)
        val lvResult = mainframe.findViewById<ListView>(R.id.lv_result)
        val dataAccess: DatabaseAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        val dictionaries = dataAccess.getDictionaries
        val dicadapter =
            ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, dictionaries)
        lvResult.adapter = dicadapter
        return mainframe
    }


}

