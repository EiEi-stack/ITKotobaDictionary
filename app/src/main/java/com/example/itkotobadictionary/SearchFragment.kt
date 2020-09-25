package com.example.itkotobadictionary

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment


class SearchFragment : Fragment() {
    private lateinit var dictionaryAdapter: ArrayAdapter<String?>
    lateinit var lvResult: ListView
    lateinit var getDictionaryList: MutableList<String?>
    var searchHistory = ArrayList<String>()
    lateinit var editor: SharedPreferences.Editor
    lateinit var set: HashSet<String>
    lateinit var searchCustomAdapter: SearchListViewAdapter
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
        set = HashSet<String>()
        editor =
            activity?.getSharedPreferences("History", Context.MODE_PRIVATE)?.edit()!!
        //set the listView
//        dictionaryAdapter =
//            context?.let {
//                ArrayAdapter(
//                    it,
//                    android.R.layout.simple_list_item_1,
//                    getDictionaryList
//                )
//            }!!
//        lvResult.adapter = dictionaryAdapter

        searchCustomAdapter =
            activity?.applicationContext?.let { SearchListViewAdapter(it,getDictionaryList()) }!!
        lvResult.adapter = searchCustomAdapter

        lvResult.setOnItemClickListener { parent, view, position, id ->
            val fragmentTransaction = activity?.getSupportFragmentManager()
                ?.beginTransaction()
            val clickeditem =
                parent.adapter.getItem(position)
            val detailFragment =
                DetailFragment() //the fragment you want to show
            val bundle = Bundle()
            bundle.putString("ITEM_NAME", clickeditem.toString())
            bundle.putString("ITEM_ID", id.toString())
            detailFragment.arguments = bundle
            fragmentTransaction
                ?.replace(
                    R.id.subMainContent,
                    detailFragment
                ) //R.id.content_frame is the layout you want to replace

            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        return mainframe
    }

    private fun setDictionaryList(): MutableList<String?> {
        val dictionaryList = getDictionaryList()
        val showListView = mutableListOf<String?>()
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

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.nav_search)
        val recentItem = menu.findItem(R.id.nav_recently_search)
        val favItem = menu.findItem(R.id.nav_favourite)
        val learnItem = menu.findItem(R.id.nav_learning)
        val langItem = menu.findItem(R.id.nav_setting_language)
//        val themeItem = menu.findItem(R.id.nav_setting_theme)
        val settingTitle = menu.findItem(R.id.setting_title)
        val learningTitle = menu.findItem(R.id.learning_title)
        searchItem.setVisible(false)
        recentItem.setVisible(false)
        favItem.setVisible(false)
        learnItem.setVisible(false)
        langItem.setVisible(false)
//        themeItem.setVisible(false)
        settingTitle.setVisible(false)
        learningTitle.setVisible(false)
        super.onPrepareOptionsMenu(menu)

    }

    private fun filter(newText: String?) {
        if (newText?.isNotEmpty()!!) {
            getDictionaryList.clear()
            var search = newText.toLowerCase()
            for (it in setDictionaryList()) {
                if (it?.toLowerCase()?.contains(search)!!) {
                    getDictionaryList.add(it)
                }
            }

            dictionaryAdapter.notifyDataSetChanged()

        } else {
            getDictionaryList.clear()
            getDictionaryList.addAll(setDictionaryList())
            dictionaryAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.nav_menu, menu)

        val searchView = activity?.findViewById<SearchView>(R.id.search_view)
        searchView?.apply {
            val searchManager = getSystemService(requireContext(), SearchManager::class.java)
            val info = searchManager!!.getSearchableInfo(
                context?.let {
                    ComponentName(
                        it,
                        MainActivity::class.java
                    )
                }
            )
            setSearchableInfo(info)
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    filter(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchHistory.add(query)

                    //store in share preferences
                    set.addAll(searchHistory)
                    editor?.putStringSet("My_HISTORY", set)
                    editor?.apply()
                    return true
                }
            })
        }
    }
}






