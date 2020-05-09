package com.krs.com.myapplication.picker

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.krs.com.myapplication.R
import com.krs.com.myapplication.util.Constants
import kotlinx.android.synthetic.main.layout_year_month_picker.*
import kotlinx.android.synthetic.main.view_filter_header.*
import java.util.*

class MonthYearPickerBottomSheet : BottomSheetDialogFragment() {

    companion object {

        fun newInstance(selectedMonth: Int, selectedYear: Int): MonthYearPickerBottomSheet =
            MonthYearPickerBottomSheet().apply {
                arguments = Bundle().apply {
                    putInt(Constants.MONTH, selectedMonth)
                    putInt(Constants.YEAR, selectedYear)
                }
            }

    }

    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var mLabel: String = ""
    private lateinit var mCallback: Callback
    private var fragmentView: View? = null
    private var dialogView: Dialog? = null
    val MONTH = 0
    val YEAR = 1
    private var mContext: Context? = null

    override fun onResume() {
        super.onResume()
        initialise()
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        dialogView= dialog
        val fragmentView = View.inflate(context,
            R.layout.layout_year_month_picker, null)
        dialogView!!.setContentView(fragmentView)
        (fragmentView!!.parent as View).setBackgroundColor(ContextCompat.getColor(context!!, android.R.color.transparent))
    }

    private fun initialise() {
        initIntent()
        initData()
        initClicks()
    }

    fun setCallback(callback: Callback) {
        this.mCallback = callback
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        //In case if any one wants to add animation for sliding of bottom sheet can be added here
        /*dialog?.window!!
            .attributes.windowAnimations = R.style.filter_dialog_animation*/
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    private fun closeScreen() {
        if (mLabel.isEmpty())
            if (mCallback != null)
                mCallback.onSubmitClicked(-1, -1)
    }

    private fun initClicks() {
        dialogView!!.btnSubmit.setOnClickListener {
            mCallback.onSubmitClicked(selectedMonth, selectedYear)
            close()
        }

        dialogView!!.iv_close.setOnClickListener {
            close()
        }

    }

    private fun initIntent() {
        selectedMonth = arguments!!.getInt(
            Constants.MONTH, Calendar.getInstance().get(
            Calendar.MONTH))
        selectedYear = arguments!!.getInt(
            Constants.YEAR, Calendar.getInstance().get(
            Calendar.YEAR))
    }

    private fun initData() {
        var adapter = MonthYearAdapter()
        val layoutManager = GridLayoutManager(activity, 4)

        dialogView!!.rvMonths.adapter = adapter
        dialogView!!.rvMonths.layoutManager = layoutManager
        adapter.setData(MONTH, getListOfMonths())
        adapter.setCallback(object : MonthYearAdapter.Callback {
            override fun onValueSelected(value: Int) {
                selectedMonth = value
            }
        })
        adapter.notifyDataSetChanged()

        var adapter1 = MonthYearAdapter()
        val layoutManager1 = GridLayoutManager(activity, 4)

        dialogView!!.rvYears.adapter = adapter1
        dialogView!!.rvYears.layoutManager = layoutManager1
        adapter1.setData(YEAR, getListOfYears())
        adapter1.setCallback(object : MonthYearAdapter.Callback {
            override fun onValueSelected(value: Int) {
                selectedYear = value
            }
        })
        adapter1.notifyDataSetChanged()
    }

    private fun getListOfYears(): Array<String> {
        return context!!.resources.getStringArray(R.array.years)
    }

    private fun getListOfMonths(): Array<String> {
        return context!!.resources.getStringArray(R.array.months)
    }

    private fun close() {
        this.dismiss()
    }

    override fun onStop() {
        super.onStop()
        closeScreen()
    }

    interface Callback {

        fun onSubmitClicked(month: Int, year: Int)

    }


}





