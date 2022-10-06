package com.ssafy.indive.view.mystudio.addsong

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.ssafy.indive.R
import com.ssafy.indive.databinding.DialogStartTimeBinding
import java.time.LocalDateTime

class StartTimeDialog(context: Context, private val listener : StartTimeDialogListener): Dialog(context) {
    private lateinit var binding: DialogStartTimeBinding
    private lateinit var startTimeValues : Array<String>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_start_time,
            null,
            false
        )
        setContentView(binding.root)

        startTimeValues = arrayOf("00", "30")

        binding.apply {
            val now = LocalDateTime.now()

            val startDateHour = now.hour

            numberpickerHour.minValue = 0
            numberpickerHour.maxValue = 23
            numberpickerHour.value = startDateHour
            //순환 안되게 막기
            numberpickerHour.wrapSelectorWheel = false

            numberpickerMinute.minValue = 0
            numberpickerMinute.maxValue = 1
            numberpickerMinute.displayedValues = startTimeValues

            numberpickerMinute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            numberpickerHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            numberpickerMinute.wrapSelectorWheel = false
        }

//        context.dialogResize(this, 0.9f, 0.5f)

        // 배경 투명하게 바꿔줌
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            tvPositive.setOnClickListener {
                val hourInt = numberpickerHour.value
                var hour = hourInt.toString()
                if(hourInt < 10){
                    hour = "0" + hour
                }
                val minute = startTimeValues[numberpickerMinute.value]
                listener.onItemClick(hour, minute)
                dismiss()
            }
        }
    }
}