package com.krs.com.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.krs.com.myapplication.util.DateUtil
import com.krs.com.myapplication.picker.MonthYearPickerBottomSheet
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnShow.setOnClickListener {
            showMonthYearPicker(0,2020)
        }
    }

    private fun showMonthYearPicker(month: Int, year: Int) {
        val callback = object : MonthYearPickerBottomSheet.Callback {
            override fun onSubmitClicked(month: Int, year: Int) {
                if (month != -1 || year != -1) {
                    tvResult.text =
                        "You selected- " + DateUtil.getDateRange(this@MainActivity, month, year)
                }
            }
        }
        val filterDialog = MonthYearPickerBottomSheet.newInstance(month, year)
        filterDialog.show(supportFragmentManager, filterDialog.tag)
        filterDialog.setCallback(callback)
    }
}
