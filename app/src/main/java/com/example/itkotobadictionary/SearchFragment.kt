package com.example.itkotobadictionary

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import java.util.*
import java.util.Locale.filter


class SearchFragment : Fragment() {
    private lateinit var dictionaryAdapter: ArrayAdapter<String?>
    lateinit var lvResult: ListView
    lateinit var getDictionaryList: MutableList<String?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val mainframe = inflater.inflate(R.layout.search_fragment, container, false)
        lvResult = mainframe.findViewById<ListView>(R.id.lv_result)
        getDictionaryList = setDictionaryList()

        dictionaryAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    getDictionaryList
                )
            }!!
        lvResult.adapter = dictionaryAdapter
        return mainframe
    }

    private fun setDictionaryList(): MutableList<String?> {
        val dictionaryList = getDictionaryList()
        val showListView = mutableListOf<String?>()
        for (i in 0 until dictionaryList.size) {
            val listData = dictionaryList[i]
            showListView.add(i,listData.name)
        }
        return showListView
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)

        val searchItem = menu?.findItem(R.id.nav_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView
        searchView.setIconifiedByDefault(false)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(context, "Query Inserted $newText", Toast.LENGTH_SHORT).show()
                filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(context, "Query submit $query", Toast.LENGTH_SHORT).show()
                return true
            }
        })
    }

    private fun filter(newText: String?) {
        if (newText != null) {
            if (newText.isEmpty()) {
                dictionaryAdapter = activity?.applicationContext?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1,setDictionaryList()) }!!
                lvResult.adapter = dictionaryAdapter
            } else {
                val getDicValue = getDictionaryList()
                for (i in 0 until getDicValue.size) {
                    val listData = getDicValue[i]
                    if (listData.name.toString().contains(newText.toLowerCase())) {
                        getDictionaryList.add(listData.name)
                    }
                }
            }
            dictionaryAdapter.notifyDataSetChanged()
        }
    }


}



