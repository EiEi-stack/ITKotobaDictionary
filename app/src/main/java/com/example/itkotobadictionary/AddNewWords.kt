package com.example.itkotobadictionary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddNewWords.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNewWords : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_words, container, false)
        val etName = view?.findViewById<EditText>(R.id.an_et_name)
        val etHiragana = view?.findViewById<EditText>(R.id.an_et_hiragna)
        val etKanji = view?.findViewById<EditText>(R.id.an_et_kanji)
        val etKatakana = view?.findViewById<EditText>(R.id.an_et_katakana)
        val btnAdd = view?.findViewById<Button>(R.id.an_btn_add)

        etName?.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                val methodManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.showSoftInput(v,InputMethodManager.SHOW_IMPLICIT)
            }
        })

        btnAdd?.setOnClickListener {
val imm= activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)

            val dictionaryItem = DictionaryClass()
            dictionaryItem.name = etName?.text.toString().trim()
            dictionaryItem.hiragana = etHiragana?.text.toString().trim()
            dictionaryItem.kanji = etKanji?.text.toString().trim()
            dictionaryItem.katakana = etKatakana?.text.toString().trim()
            val dataAccess: DatabaseAccess = context?.applicationContext?.let {
                DatabaseAccess.getInstance(
                    it
                )
            }!!
            dataAccess.open()
            if (dictionaryItem.name.isNotEmpty() && dictionaryItem.hiragana.isNotEmpty()) {
                val isSuccess = dataAccess.addNew(dictionaryItem)

                if (isSuccess) {
                    Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show()
                    val fragment= SearchFragment()
                    val fragmentManager= fragmentManager
                    val fragmentTransaction= fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.subMainContent,fragment)
                    fragmentTransaction?.commit()
                }
            } else {
                Toast.makeText(
                    context,
                    "You must enter Name and Hiragana,at least",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddNewWords.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewWords().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
