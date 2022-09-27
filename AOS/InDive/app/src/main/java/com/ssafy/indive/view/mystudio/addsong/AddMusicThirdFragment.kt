package com.ssafy.indive.view.mystudio.addsong

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.indive.R
import com.ssafy.indive.base.BaseFragment
import com.ssafy.indive.databinding.FragmentAddSongThirdBinding
import com.ssafy.indive.utils.RESERVATION_DAY
import com.ssafy.indive.utils.START_DAY
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@AndroidEntryPoint
class AddMusicThirdFragment :
    BaseFragment<FragmentAddSongThirdBinding>(R.layout.fragment_add_song_third) {

    private val addMusicViewModel: AddMusicViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun init() {
        binding.apply {
            addMusicVM = addMusicViewModel
        }
        initViewModelCallback()
        clickListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewModelCallback() {
        addMusicViewModel.initDate()

        addMusicViewModel.successAddMusic.observe(viewLifecycleOwner) {
            showToast(it)
            addMusicViewModel.refreshInit()
            findNavController().navigate(R.id.action_addSongThirdFragment_to_homeFragment)
        }
        addMusicViewModel.failedAddMusic.observe(viewLifecycleOwner) {
            showToast(it)
        }
    }


    private fun clickListener() {
        binding.apply {
            btnAddsongThird.setOnClickListener {
                addMusicViewModel.addMusic()
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
        val startTimeDialogListener: StartTimeDialogListener = object : StartTimeDialogListener {
            override fun onItemClick(hour: String, minute: String) {

                if (flag == START_DAY) {
                    addMusicViewModel.setStartTime("$hour:$minute")
                } else {
                    addMusicViewModel.setReservationTime("$hour:$minute")
                }
            }
        }
        StartTimeDialog(requireContext(), startTimeDialogListener).show()
    }

    private fun initDatePickerDialog(flag: Int) {
        val calendar = Calendar.getInstance()
        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dateTimeFormat.format(date)
        val todayCalendarType = dateTimeFormat.parse(today)

        calendar.time = todayCalendarType

        val dataSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthInt, dayOfMonthInt ->
                var month = (monthInt + 1).toString()
                if (monthInt + 1 < 10) {
                    month = "0" + month
                }

                var dayOfMonth = dayOfMonthInt.toString()
                if (dayOfMonthInt < 10) {
                    dayOfMonth = "0" + dayOfMonth
                }

                if (flag == START_DAY) {
                    addMusicViewModel.setStartDate("${year}-${month}-${dayOfMonth}")

                } else {
                    addMusicViewModel.setReservationDate("${year}-${month}-${dayOfMonth}")
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

        datePickerDialog.show()
    }
}