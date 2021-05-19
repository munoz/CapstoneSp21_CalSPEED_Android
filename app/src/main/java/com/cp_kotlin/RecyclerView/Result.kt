package com.cp_kotlin.RecyclerView

import android.content.Context
import android.os.Parcelable
import com.cp_kotlin.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Result(
    val name: String,
    val id: Long,
    val latency: Double
):
    Parcelable {
    companion object {
        fun resultsList(context: Context): List<Result> {
            return listOf(
                    Result("Result 1", 0, 1.0),
                    Result("Result 2", 1, 12.5),
                    Result("Result 3", 2, 67.2)
            )
        }
    }
}