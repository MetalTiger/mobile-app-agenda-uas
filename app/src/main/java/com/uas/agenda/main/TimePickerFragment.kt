package com.uas.agenda.main

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.uas.agenda.R
import java.util.*

class TimePickerFragment(val listener: (String) -> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hora = calendar.get(Calendar.HOUR_OF_DAY)
        val minutos = calendar.get(Calendar.MINUTE)
        val dialog = TimePickerDialog(activity as Context, R.style.TimePicker,this, hora, minutos, false)

        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        if (minute < 10) {
            listener("${hourOfDay}:0${minute}")

        } else {
            listener("${hourOfDay}:${minute}")

        }

    }

}