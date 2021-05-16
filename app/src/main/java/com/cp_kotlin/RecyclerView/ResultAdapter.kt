package com.cp_kotlin.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cp_kotlin.R
import java.util.*

class ResultAdapter(
    private val resultsList: List<Result>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<ResultAdapter.ResultAdapterHolder>() {

    class ResultAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.resultName)

        fun bind(
                results: Result,
                onClickListener: OnClickListener
        ) {
            title.text = results.name
            itemView.setOnClickListener {
                onClickListener.onClick(results)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapterHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.result_row, parent, false)
        return ResultAdapterHolder(view)
    }

    override fun onBindViewHolder(holder: ResultAdapterHolder, position: Int) {
        val results: Result = resultsList[position]
        holder.bind(results, onClickListener)
    }

    override fun getItemCount(): Int {
        return resultsList.size
    }

    class OnClickListener(val clickListener: (Result) -> Unit) {
        fun onClick(
                results: Result
        ) = clickListener(results)
    }
}