package com.cp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.RecyclerView.ResultAdapter
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.android.synthetic.main.fragment_2.*
import java.util.*

class Fragment2 : Fragment() {
    var name = ArrayList<String>()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ResultAdapter.ViewHolder>? = null

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

        for (i in 0..10) {
            name.add("Result $i")
        }

        recyclerViewResults.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ResultAdapter(name)
        }
    }
}