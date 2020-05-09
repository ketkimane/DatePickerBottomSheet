package com.krs.com.myapplication.util

import android.content.Context
import com.krs.com.myapplication.R
import java.util.*

object DateUtil {

    fun getDateRange(context: Context, month: Int, year: Int): String {
        val monthArray = context.resources.getStringArray(R.array.months)
        val cal = Calendar.getInstance()
        cal.set(Calendar.MONTH, month)
       // val lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val fromDate =  monthArray[month] + " " + year
       // val toDate = "" + lastDayOfMonth + " " + monthArray[month] + " " + year
        return "$fromDate"
    }
}