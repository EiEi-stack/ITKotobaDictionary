package com.example.itkotobadictionary

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
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
        view == `object` as RelativeLayout


    override fun getCount(): Int = textSlider.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slider_item, container, false)
        val textSlide = view.findViewById<TextView>(R.id.tv_slider)
        if (textSlider != null) {
            textSlide.setText(textSlider[position])
        }
        container!!.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container!!.removeView(`object` as RelativeLayout)
    }

}