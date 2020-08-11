package com.example.itkotobadictionary

import android.os.Build
import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import android.widget.Toolbar
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {
    lateinit var getItemName: String
    var getItemId: String? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getItemName = arguments?.getString("ITEM_NAME").toString()
        getItemId = arguments?.getString("ITEM_ID")
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val tv_detail = view.findViewById<TextView>(R.id.tv_detailText)
        val tv_detailHiragana = view.findViewById<TextView>(R.id.tv_detailHiragana)
        val tv_detailKatakana = view.findViewById<TextView>(R.id.tv_detailKantakana)
        val tv_detailKanji = view.findViewById<TextView>(R.id.tv_detailKanji)
        val toggleFavourite = view.findViewById<ToggleButton>(R.id.myToggleFavourite)
        tv_detail?.setText(getItemName)
        var listData = DictionaryClass()
        var getHiragana = ""
        var getKatakana = ""
        var getKanji = ""
        var getDicValue = getDictionaryList()
        for (i in 0 until getDicValue.size) {
            listData = getDicValue[i]
            if (listData.name.contains(getItemName.toString())) {
                getHiragana = listData.hiragana
                getKatakana = listData.katakana
                getKanji = listData.kanji
                if (listData.favouriteStatus == 1) {
                    toggleFavourite.background =
                        resources.getDrawable(R.drawable.ic_star_black_24dp)
                } else {
                    toggleFavourite.background =
                        resources.getDrawable(R.drawable.ic_star_border_black_24dp)
                }
            }
        }
        tv_detailHiragana.setText(getHiragana)
        tv_detailKatakana.setText("(" + getKatakana + ")")
        tv_detailKanji.setText(getKanji)

        toggleFavourite.setOnCheckedChangeListener { buttonView, isChecked ->

            val getId = getItemId?.toLongOrNull()
            if (isChecked) {
                toggleFavourite.background = resources.getDrawable(R.drawable.ic_star_black_24dp)
                if (getId != null) {
                    updateFavourite(getId, 1)
                }
            } else {
                toggleFavourite.background =
                    resources.getDrawable(R.drawable.ic_star_border_black_24dp)
                if (getId != null) {
                    updateFavourite(getId, 0)

                }
            }
        }
// Inflate the layout for this fragment
        return view

    }

    private fun getDictionaryList(): MutableList<DictionaryClass> {
        val dataAccess: DatabaseAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        val dictionaries = dataAccess.getDictionaries
        return dictionaries
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    private fun updateFavourite(itemId: Long, isFavourite: Int) {
        val dataAccess: DatabaseAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        dataAccess.updateFavourite(itemId + 1, isFavourite)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
