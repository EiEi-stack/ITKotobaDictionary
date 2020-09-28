package com.example.itkotobadictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class SearchListViewAdapter(private val context: Context,private val dataSource: MutableList<DictionaryClass>) : BaseAdapter(){
   private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      val  layoutView= inflater.inflate(R.layout.sub_list_search_fragment, parent, false)
        val txtHiragana = layoutView?.findViewById<TextView>(R.id.txt_search_hiragana)
        val btnDelete = layoutView?.findViewById<Button>(R.id.btn_search_delete)
        val dictionaryItem =getItem(position) as DictionaryClass

        txtHiragana?.text= dictionaryItem.name
        return layoutView
    }

    override fun getItem(position: Int): Any {
       return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
      return position.toLong()
    }

    override fun getCount(): Int {
      return dataSource.size
    }

//    private val layoutInflater =
//        context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var view = convertView
//        if (convertView == null) {
//            view = layoutInflater.inflate(R.layout.sub_list_search_fragment, parent, false)
//        }
//
//        val txtHiragana = view?.findViewById<TextView>(R.id.txt_search_hiragana)
//        txtHiragana?.text = "Hiragana"
//
//        val btnDelete = view?.findViewById<Button>(R.id.btn_search_delete)
//        return view!!
//    }

}