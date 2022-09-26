package com.ssafy.indive.view.userstudio.donate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ssafy.indive.databinding.DialogFingerprintBinding

class FingerPrintDialog : DialogFragment() {

    private lateinit var binding: DialogFingerprintBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFingerprintBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()

    }

    private fun initClickListener() {
        binding.btnConfirm.setOnClickListener {
            Log.d("FingerPrintDialog", "dismiss: ")
            dismiss()
        }
    }


}