package com.ssafy.indive.view.mystudio

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.ssafy.indive.databinding.DialogNoticeBinding
import com.ssafy.indive.model.dto.Notice
import com.ssafy.indive.utils.USER
import com.ssafy.indive.view.login.MemberViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "NoticeDialog"
@AndroidEntryPoint
class NoticeDialog(str: String, var listener: (String) -> (Unit)): DialogFragment() {
    private lateinit var binding: DialogNoticeBinding
    private var notice: String = str
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogNoticeBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etConfirmText.setText(notice)
        initClickListener()
    }

    private fun initClickListener() {
        binding.apply {
            btnConfirm.setOnClickListener {
                // 공지사항 변경
                Log.d(TAG, "initClickListener: ${sharedPreferences.getLong(USER, 0)}")
                listener(etConfirmText.text.toString())
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }


}