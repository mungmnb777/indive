package com.ssafy.indive.view.home

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssafy.indive.MainViewModel
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentMoreDialogBindingImpl
import com.ssafy.indive.databinding.FragmentSetPrivateKeyDialogBinding
import com.ssafy.indive.utils.getDeviceSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetPrivateKeyDialogFragment : DialogFragment() {
    private val homeViewModel: HomeViewModel by viewModels<HomeViewModel>()

    private lateinit var binding : FragmentSetPrivateKeyDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSetPrivateKeyDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListener()
    }

    private fun initClickListener(){
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                val privateKey = etPrivateKey.text.toString()
                homeViewModel.savePrivateKey(privateKey)
                Toast.makeText(requireActivity(), "저장되었습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes

        // 앱을 실행한 디바이스의 가로, 세로 크기를 가져온다.
        val deviceWidth = getDeviceSize(requireActivity()).x

        // 다이얼로그 크기를 디바이스 가로의 90%로 설정한다.
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}