package com.example.itkotobadictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter

class SliderAdapter : PagerAdapter {
    var context: Context
    private var textSlider: MutableList<String>
    lateinit var inflater: LayoutInflater

    constructor(context: Context, textSlider: MutableList<String>) : super() {
        this.context = context
        this.textSlider = textSlider
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object` as LinearLayout


    override fun getCount(): Int = textSlider.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slider_item, container, false)
        val textSlide = view.findViewById<TextView>(R.id.tv_slider)
        val textSlideMeaning = view.findViewById<TextView>(R.id.tv_meaning_slider)
        if (textSlider != null) {
            textSlide.setText(textSlider[position])
        }
        view.setOnClickListener(View.OnClickListener {
            var meaning: String = ""
            for (it in getDictionaryList()) {
                if (textSlider[position] == it.name) {
                    meaning = it.hiragana + "\n\r" + it.katakana + "\n\r" + it.kanji
                }
            }
            textSlideMeaning.setText(meaning)

        })
        container!!.addView(view)
        return view
    }


    private fun setDictionaryList(): MutableList<String> {
        val dictionaryList = getDictionaryList()
        val showListView = mutableListOf<String>()
        for (i in 0 until dictionaryList.size) {
            val listData = dictionaryList[i]
            showListView.add(i, listData.name)
        }
        return showListView
    }

    private fun getDictionaryList(): MutableList<DictionaryClass> {
        val dataAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        val dictionaries = dataAccess.getDictionaries
        return dictionaries
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as LinearLayout)
    }

}