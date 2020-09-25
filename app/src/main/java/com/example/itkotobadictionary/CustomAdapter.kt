package com.example.itkotobadictionary

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(
    val activity: FragmentActivity?,
    private val dictionaryList: MutableList<DictionaryClass>
) :RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    //Viewの初期化
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val hiragana: TextView
        val katakana: TextView
        val kanji: TextView
        val toggleFavourite: ToggleButton

        init {
            name = view.findViewById(R.id.tv_fav_name)
            hiragana = view.findViewById(R.id.tv_fav_hiragana)
            katakana = view.findViewById(R.id.tv_fav_katakana)
            kanji = view.findViewById(R.id.tv_fav_kanji)
            toggleFavourite = view.findViewById<ToggleButton>(R.id.myToggleButton)

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
        if (dictionaryView.favouriteStatus == 1) {

            holder.toggleFavourite.background =
                activity?.resources?.getDrawable(R.drawable.ic_star_black_24dp)
        } else {

            holder.toggleFavourite.background =
                activity?.resources?.getDrawable(R.drawable.ic_star_border_black_24dp)
        }
        holder.toggleFavourite.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                holder.toggleFavourite.background =
                    activity?.resources?.getDrawable(R.drawable.ic_star_black_24dp)
                if (position != null) {
                    val id = dictionaryView.id
                    updateFavourite(id, 1)
                }
            } else {
                holder.toggleFavourite.background =
                    activity?.resources?.getDrawable(R.drawable.ic_star_border_black_24dp)
                if (position != null) {
                    val id = dictionaryView.id
                    updateFavourite(id, 0)
                }
            }
        }
    }


    private fun updateFavourite(itemId: Long, isFavourite: Int) {
        val dataAccess: DatabaseAccess = activity?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        dataAccess.updateFavourite(itemId, isFavourite)

    }
}