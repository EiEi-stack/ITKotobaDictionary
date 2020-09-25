package com.example.itkotobadictionary

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class SearchListViewAdapter(context: Context,dictionaryList:List<DictionaryClass>) :
    ArrayAdapter<DictionaryClass>(context,0,dictionaryList) {

    private val layoutInflater =
        context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.sub_list_search_fragment, parent, false)
        }

        val txtHiragana = view?.findViewById<TextView>(R.id.txt_search_hiragana)
        txtHiragana?.text = "Hiragana"

        val btnDelete = view?.findViewById<Button>(R.id.btn_search_delete)
        return view!!
    }

}