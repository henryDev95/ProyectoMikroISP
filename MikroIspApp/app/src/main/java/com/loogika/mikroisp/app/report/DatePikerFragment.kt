package com.loogika.mikroisp.app.report

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.loogika.mikroisp.app.R
import java.util.*

class DatePikerFragment(val listener:(day:Int,month:Int,year:Int)->Unit):DialogFragment(),DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth,month,year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val  c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONDAY)
        val year = c.get(Calendar.YEAR)
        val piker = DatePickerDialog(activity as Context,this,year,month,day)
        return piker
    }
}