package com.ssafy.indive.view.mystudio.addsong

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongThirdBinding
import com.ssafy.indive.utils.RESERVATION_DAY
import com.ssafy.indive.utils.START_DAY
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AddMusicThirdFragment : BaseFragment<FragmentAddSongThirdBinding>(R.layout.fragment_add_song_third) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        initDate()
        clickListnener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initDate() {
        val now = LocalDateTime.now()
        val startDateYear = now.year
        val startDateMonthInt = now.monthValue
        var startDateMonth = startDateMonthInt.toString()
        if(startDateMonthInt < 10){
            startDateMonth = "0" + startDateMonth
        }
        val startDateDayInt = now.dayOfMonth
        var startDateDay = startDateDayInt.toString()
        if(startDateDayInt < 10){
            startDateDay = "0" + startDateDay
        }

        val startDateHour = now.hour
        val startDateMinuteInt = now.minute
        var startDateMinute = startDateMinuteInt.toString()
        if(startDateMinuteInt < 10){
            startDateMinute = "0" + startDateMinute
        }

        binding.apply {
            btnStartDay.text = "${startDateYear}.${startDateMonth}.${startDateDay}"
            btnStartTime.text = "${startDateHour} : ${startDateMinute}"
            btnReservationDay.text = "${startDateYear}.${startDateMonth}.${startDateDay}"
            btnReservationTime.text = "${startDateHour} : ${startDateMinute}"
        }
    }

    private fun clickListnener() {
        binding.apply {
            btnAddsongThird.setOnClickListener {
                findNavController().navigate(R.id.action_addSongThirdFragment_to_myStudioFragment)
            }
            btnStartDay.setOnClickListener {
                initDatePickerDialog(START_DAY)
            }
            btnStartTime.setOnClickListener {
                initTimeDialog(START_DAY)
            }
            btnReservationDay.setOnClickListener {
                initDatePickerDialog(RESERVATION_DAY)
            }
            btnReservationTime.setOnClickListener {
                initTimeDialog(RESERVATION_DAY)
            }
        }
    }
    private fun initTimeDialog(flag: Int) {
        val startTimeDialogListener: StartTimeDialogListener = object: StartTimeDialogListener{
            override fun onItemClick(hour: String, minute: String) {
                if (flag == START_DAY){
                    binding.btnStartTime.text = "${hour} : ${minute}"
                } else{
                    binding.btnReservationTime.text = "${hour} : ${minute}"
                }
            }
        }
        StartTimeDialog(requireContext(), startTimeDialogListener).show()
    }

    private fun initDatePickerDialog(flag: Int) {
        val calendar = Calendar.getInstance()
        val minDate = Calendar.getInstance()
        val maxDate = Calendar.getInstance()

        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dateTimeFormat.format(date)
        val todayCalendarType = dateTimeFormat.parse(today)

        calendar.time = todayCalendarType

        val dataSetListener = DatePickerDialog.OnDateSetListener { view, year, monthInt, dayOfMonthInt ->
            var month = (monthInt + 1).toString()
            if(monthInt + 1 < 10){
                month = "0" + month
            }

            var dayOfMonth = dayOfMonthInt.toString()
            if(dayOfMonthInt < 10){
                dayOfMonth = "0" + dayOfMonth
            }

            if(flag == START_DAY){
                binding.btnStartDay.text = "${year}.${month}.${dayOfMonth}"
            } else{
                binding.btnReservationDay.text = "${year}.${month}.${dayOfMonth}"
            }
        }
        val datePickerDialog: DatePickerDialog =
            DatePickerDialog(
                requireContext(),
                dataSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

        minDate.time = todayCalendarType
        minDate.add(Calendar.DAY_OF_MONTH, 1)
        maxDate.time = todayCalendarType
        maxDate.add(Calendar.MONTH, 6)

        datePickerDialog.datePicker.minDate = minDate.time.time
        datePickerDialog.datePicker.maxDate = maxDate.timeInMillis

        datePickerDialog.show()
    }
}