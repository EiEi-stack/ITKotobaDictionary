package com.example.itkotobadictionary

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment


class SearchFragment : Fragment() {
    private lateinit var lvResult: ListView
    private lateinit var searchView: SearchView
    private lateinit var showSearchedResult: MutableList<DictionaryClass>
    var searchHistory = ArrayList<String>()
    lateinit var editor: SharedPreferences.Editor
    lateinit var set: HashSet<String>
    lateinit var searchCustomAdapter: SearchListViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainframe = inflater.inflate(R.layout.search_fragment, container, false)
        lvResult = mainframe.findViewById<ListView>(R.id.lv_result)
        searchView = mainframe.findViewById<SearchView>(R.id.search_view)
        showSearchedResult = getDictionaryList()
        set = HashSet<String>()
        editor =
            activity?.getSharedPreferences("History", Context.MODE_PRIVATE)?.edit()!!
        searchCustomAdapter = SearchListViewAdapter(activity, showSearchedResult)
        lvResult.adapter = searchCustomAdapter


        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText
                if (text != null) {
                    searchCustomAdapter.filter(text)
                }
                return false
            }

        })
        return mainframe
    }

    private fun getDictionaryList(): MutableList<DictionaryClass> {
        val dataAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        return dataAccess.getDictionaries
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.nav_search)
        val recentItem = menu.findItem(R.id.nav_recently_search)
        val favItem = menu.findItem(R.id.nav_favourite)
        val learnItem = menu.findItem(R.id.nav_learning)
        val langItem = menu.findItem(R.id.nav_setting_language)
        val settingTitle = menu.findItem(R.id.setting_title)
        val learningTitle = menu.findItem(R.id.learning_title)
        searchItem.isVisible = false
        recentItem.isVisible = false
        favItem.isVisible = false
        learnItem.isVisible = false
        langItem.isVisible = false
        settingTitle.isVisible = false
        learningTitle.isVisible = false
        super.onPrepareOptionsMenu(menu)

    }
    private fun filter(newText: String?) {
        if (newText?.isNotEmpty()!!) {
            showSearchedResult.clear()
            var search = newText.toLowerCase()
            for (it in getDictionaryList()) {
                if (it?.name.toLowerCase()?.contains(search)!!) {
                    showSearchedResult.add(it)
                }
            }
            searchCustomAdapter= SearchListViewAdapter(activity,showSearchedResult)
            lvResult.adapter = searchCustomAdapter
        } else {
            showSearchedResult.clear()
            showSearchedResult.addAll(getDictionaryList())
            searchCustomAdapter= SearchListViewAdapter(activity,showSearchedResult)
            lvResult.adapter = searchCustomAdapter
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        menu.clear()
//        inflater.inflate(R.menu.nav_menu, menu)
//
//        val searchView = activity?.findViewById<SearchView>(R.id.search_view)
//        searchView?.apply {
//            val searchManager = getSystemService(requireContext(), SearchManager::class.java)
//            val info = searchManager!!.getSearchableInfo(
//                context?.let {
//                    ComponentName(
//                        it,
//                        MainActivity::class.java
//                    )
//                }
//            )
//            setSearchableInfo(info)
//            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    filter(newText)
//                    return true
//                }
//
//                override fun onQueryTextSubmit(query: String): Boolean {
//                    searchHistory.add(query)
//                    set.addAll(searchHistory)
//                    editor?.putStringSet("My_HISTORY", set)
//                    editor?.apply()
//                    return true
//                }
//            })
//        }
//    }
}






