package com.krs.com.myapplication.picker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.krs.com.myapplication.R
import kotlinx.android.synthetic.main.row_item_month_year_picker.view.*

class MonthYearAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tvValue.text = dataset.get(position)
        holder.itemView.tvValue.isEnabled = false
        holder.itemView.setOnClickListener {
            currentPosition = position
            notifyItemChanged(position)
            notifyItemChanged(oldPosition)
            oldPosition = currentPosition
            when (type) {
                MONTH ->
                    mCallback.onValueSelected(position)
                YEAR ->
                    mCallback.onValueSelected(dataset.get(position).toInt())
            }
        }
        if (currentPosition == position)
            holder.itemView.tvValue.isEnabled = true
    }

    private var currentPosition: Int = 0
    private lateinit var mContext: Context
    private var oldPosition: Int = 0
    lateinit var dataset: Array<String>
    var type: Int = 0
    lateinit var mCallback: Callback
    val MONTH = 0
    val YEAR = 1

    fun setData(type: Int, data: Array<String>) {
        this.type = type
        this.dataset = data
        notifyDataSetChanged()
    }


    fun setCallback(callback: Callback) {
        this.mCallback = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.mContext = parent.context
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_month_year_picker, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        init {
            itemView.tvValue.visibility = View.VISIBLE
        }
    }

    interface Callback {

        fun onValueSelected(month: Int)

    }


}

