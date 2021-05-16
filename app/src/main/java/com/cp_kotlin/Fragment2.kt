package com.cp_kotlin

import DB.AppDatabase
import DB.speedResultsdao
import Java.speedResults
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import Java.speedResultsAdapter
import android.util.Log
import androidx.room.Room
import com.cp_kotlin.RecyclerView.Result
import java.util.*

class Fragment2 : Fragment() {
    var mSpeedResults: speedResultsdao? = null
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

        mSpeedResults =
            this.context?.let {
                Room.databaseBuilder(it, AppDatabase::class.java, AppDatabase.dbName)
                    .allowMainThreadQueries()
                    .build()
                    .getspeedResultsdao()
            }

        recyclerView = itemView.findViewById(R.id.recyclerViewResults)
        val list = mSpeedResults?.speedResults
        val adapter = speedResultsAdapter(list,resultItemListener)
        recyclerView.adapter = adapter
    }

    private val resultItemListener = speedResultsAdapter.OnClickListener { results ->
        val direction: NavDirections =
                Fragment2Directions.actionFragment2ToFragment4(results as speedResults)
        findNavController().navigateUp()
        //findNavController().navigate(direction)
    }
}