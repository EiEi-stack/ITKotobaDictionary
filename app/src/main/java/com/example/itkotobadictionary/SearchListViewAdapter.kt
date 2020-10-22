package com.example.itkotobadictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import java.util.*

class SearchListViewAdapter(
    private val activity: FragmentActivity?,
    private var dataSource: MutableList<DictionaryClass>
) : BaseAdapter() {
    private val inflater =
        activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var tempList: MutableList<DictionaryClass>
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutView = inflater.inflate(R.layout.sub_list_search_fragment, parent, false)
        val txtHiragana = layoutView?.findViewById<TextView>(R.id.txt_search_hiragana)
        val btnDelete = layoutView?.findViewById<Button>(R.id.btn_search_delete)
        val dictionaryItem = getItem(position) as DictionaryClass

        txtHiragana?.text = dictionaryItem.name
        txtHiragana?.setOnClickListener(View.OnClickListener {
            val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
            val detailFragment =
                DetailFragment() //the fragment you want to show
            val bundle = Bundle()
            bundle.putString("ITEM_NAME", dictionaryItem.name)
            bundle.putString("ITEM_ID", position.toString())
            detailFragment.arguments = bundle
            fragmentTransaction
                ?.replace(
                    R.id.subMainContent,
                    detailFragment
                )

            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        })
        btnDelete?.setOnClickListener(View.OnClickListener {
            val dataAccess = activity?.applicationContext?.let {
                DatabaseAccess.getInstance(
                    it
                )
            }!!
            dataAccess.open()
            val dictionary = DictionaryClass()
            dictionary.name = dictionaryItem.name
            dictionary.hiragana = dictionaryItem.hiragana
            dictionary.katakana = dictionaryItem.katakana
            dictionary.kanji = dictionaryItem.kanji
            dictionary.isDeleted = 1
            val result = dataAccess.deleteItem(dictionary)
            if (result == 1) {
                Toast.makeText(activity, "アイテムを削除しました。", Toast.LENGTH_SHORT).show()
                updateResults(dataAccess.getDictionaries)
            }
        })
        return layoutView
    }
    fun getDictionaryItem(): MutableList<DictionaryClass> {
        val dataAccess = activity?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        return dataAccess.getDictionaries
    }
    fun filter(text:String){
        val textResult = text.toLowerCase(Locale.getDefault())
        dataSource.clear()
        val getDictionaryList =getDictionaryItem()
        if(textResult.length==0){
            dataSource.addAll(getDictionaryList)
        }
        else{
            for(i in 0..getDictionaryList.size-1){
                if(getDictionaryList.get(i).name.toLowerCase(Locale.getDefault()).contains(text)){
                    dataSource.add(getDictionaryList.get(i))
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun updateResults(result:MutableList<DictionaryClass> ){
        dataSource =result
        notifyDataSetChanged()
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
}
