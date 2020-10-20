package com.example.itkotobadictionary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReportPloblem.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportPloblem : Fragment() {
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
        val view =inflater.inflate(R.layout.fragment_report_ploblem, container, false)
        val sendButton = view.findViewById<Button>(R.id.btn_report_send)
        val etEmail = view.findViewById<EditText>(R.id.et_report_toEmail)
        val etSubject = view.findViewById<EditText>(R.id.et_report_subject)
        val etMessage = view.findViewById<EditText>(R.id.et_report_message)
        // Inflate the layout for this fragment
        sendButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val fragment = SearchFragment()
            intent.data = Uri.parse("mailto: eieihan.94@gmail.com")
            intent.putExtra(Intent.EXTRA_EMAIL,etEmail.text.toString())
            intent.putExtra(Intent.EXTRA_SUBJECT,etSubject.text.toString())
            intent.putExtra(Intent.EXTRA_TEXT,etMessage.text.toString())
            startActivity(Intent.createChooser(intent,"choose an Email client"))
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.subMainContent,fragment)?.commit()
        }
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
         * @return A new instance of fragment ReportPloblem.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReportPloblem().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
