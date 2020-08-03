package com.example.itkotobadictionary

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment


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
        val intent = activity?.intent
        if (Intent.ACTION_SEARCH == intent?.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(
                    activity?.applicationContext,
                    MySuggestionProvider.AUTHORITY,
                    MySuggestionProvider.MODE
                )
                    .saveRecentQuery(query, null)
            }
        }
        // Inflate the layout for this fragment
        val mainframe = inflater.inflate(R.layout.search_fragment, container, false)
        lvResult = mainframe.findViewById<ListView>(R.id.lv_result)
        getDictionaryList = setDictionaryList()
        //set the listView
        dictionaryAdapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_list_item_1,
                    getDictionaryList
                )
            }!!
        lvResult.adapter = dictionaryAdapter

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
        val dataAccess: DatabaseAccess = context?.applicationContext?.let {
            DatabaseAccess.getInstance(
                it
            )
        }!!
        dataAccess.open()
        val dictionaries = dataAccess.getDictionaries
        return dictionaries
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu?.findItem(R.id.nav_search)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView

        searchView.isQueryRefinementEnabled = true
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.setQuery("", false)
                searchView.setIconifiedByDefault(true)
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)
    }

    private fun filter(newText: String?) {
        if (newText?.isNotEmpty()!!) {
            getDictionaryList.clear()
            var listData = DictionaryClass()
            val getDicValue = getDictionaryList()
            for (i in 0 until getDicValue.size) {
                listData = getDicValue[i]
                if (listData.name.contains(newText.toLowerCase())) {
                    getDictionaryList.add(newText)
                }
            }

            dictionaryAdapter.notifyDataSetChanged()
        } else {
            getDictionaryList.clear()
            getDictionaryList.addAll(setDictionaryList())
            dictionaryAdapter.notifyDataSetChanged()
        }
    }


}



