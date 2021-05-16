package com.cp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.RecyclerView.ResultAdapter
import com.cp_kotlin.RecyclerView.Result
import java.util.*

class Fragment2 : Fragment() {
    var name = ArrayList<String>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_2, container, false)
        return rootView
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

        recyclerView = itemView.findViewById(R.id.recyclerViewResults)
        val list = Result.resultsList(requireContext())
        val adapter = ResultAdapter(list,resultItemListener)
        recyclerView.adapter = adapter
    }

    private val resultItemListener = ResultAdapter.OnClickListener { results ->
        val direction: NavDirections =
                Fragment2Directions.actionFragment2ToFragment4(results)
        findNavController().navigateUp()
        //findNavController().navigate(direction)
    }
}