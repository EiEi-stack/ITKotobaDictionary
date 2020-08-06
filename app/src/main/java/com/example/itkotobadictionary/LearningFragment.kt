package com.example.itkotobadictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var isShowingBackLayout = false
var mIsBackVisible by Delegates.notNull<Boolean>()

/**
 * A simple [Fragment] subclass.
 * Use the [LearningFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LearningFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var textSlider: MutableList<String> = mutableListOf("TinTin","ZunZun","NilarOo")


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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_learning, container, false)
        val viewpager = view.findViewById<ViewPager>(R.id.viewpager)
        val pagerAdapter = activity?.applicationContext?.let { SliderAdapter(it, setDictionaryList()) }
        viewpager.adapter = pagerAdapter
//        val study_flip_container = view.findViewById<FrameLayout>(R.id.study_flip_container)
//        if (savedInstanceState == null) {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.study_flip_container, StudyFlipFrontFragment())?.commit()
//
//        } else {
//            isShowingBackLayout = fragmentManager?.backStackEntryCount!! > 0
//        }
//
//        val fragmentManager =
//            study_flip_container.setOnClickListener {
//                flipCard()
//            }
        return view
    }

//    private fun flipCard() {
//        if (isShowingBackLayout) {
//            fragmentManager?.beginTransaction()
//                ?.replace(R.id.study_flip_container, StudyFlipFrontFragment())?.commit()
//
//        }
//        isShowingBackLayout = true;
//        fragmentManager?.beginTransaction()
//            ?.setCustomAnimations(
//                R.animator.cardflip_right_in, R.animator.cardflip_right_out,
//                R.animator.cardflip_left_in, R.animator.cardflip_left_out
//            )
//            ?.replace(R.id.study_flip_container, StudyFlipBackFragment())
//            ?.addToBackStack(null)
//            ?.commit();
//    }
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LearningFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LearningFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}