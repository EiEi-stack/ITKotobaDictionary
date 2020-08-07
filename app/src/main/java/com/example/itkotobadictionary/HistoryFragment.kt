package com.example.itkotobadictionary

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var lvResult: ListView
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainView = inflater.inflate(R.layout.fragment_history, container, false)
        val sharePreferences = activity?.getSharedPreferences("History", Activity.MODE_PRIVATE)
        val historySearch = sharePreferences?.getStringSet("My_HISTORY", null)
        lvResult = mainView.findViewById<ListView>(R.id.lv_history)

        ////
        if (historySearch != null) {
            for (it in historySearch) {
                val searchHistoryResult = historySearch.toTypedArray()
                val dictionaryAdapter =
                    context?.let {
                        ArrayAdapter(
                            it,
                            android.R.layout.simple_list_item_1,
                            searchHistoryResult
                        )
                    }!!
                lvResult.adapter = dictionaryAdapter
            }
        }
//        if (arguments?.getSerializable("HISTORY") != null) {
//            searchHistoryResult = arguments?.getSerializable("HISTORY") as ArrayList<String>
//            val dictionaryAdapter =
//                context?.let {
//                    ArrayAdapter(
//                        it,
//                        android.R.layout.simple_list_item_1,
//                        searchHistoryResult
//                    )
//                }!!
//            lvResult.adapter = dictionaryAdapter
//        }
        //set the listView

        return mainView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
