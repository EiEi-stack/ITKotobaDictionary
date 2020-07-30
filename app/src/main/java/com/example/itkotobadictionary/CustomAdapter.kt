package com.example.itkotobadictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val dictionaryList: MutableList<DictionaryClass>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //Viewの初期化
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val hiragana: TextView
        val katakana: TextView
        val kanji: TextView

        init {
            name = view.findViewById(R.id.tv_fav_name)
            hiragana = view.findViewById(R.id.tv_fav_hiragana)
            katakana = view.findViewById(R.id.tv_fav_katakana)
            kanji = view.findViewById(R.id.tv_fav_kanji)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dictionaryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dictionaryView = dictionaryList[position]
        holder.name.text = dictionaryView.name
        holder.hiragana.text = dictionaryView.hiragana
        holder.katakana.text = "(" + dictionaryView.katakana + ")"
        holder.kanji.text = dictionaryView.kanji
    }
}