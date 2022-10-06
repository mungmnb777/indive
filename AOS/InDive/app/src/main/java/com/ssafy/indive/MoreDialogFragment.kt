package com.ssafy.indive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ssafy.indive.databinding.FragmentMoreDialogBinding

class MoreDialogFragment(
    private val moreDialogClickListener: MoreDialogClickListener
) : DialogFragment() {

    private lateinit var binding: FragmentMoreDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more_dialog, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvGoDetail.setOnClickListener {
            moreDialogClickListener.clickDetail()
            dismiss()
        }
        binding.cvGoStudio.setOnClickListener {
            moreDialogClickListener.clickStudio()
            dismiss()
        }
        binding.cvReport.setOnClickListener {
            moreDialogClickListener.clickReport()
        }

    }

    interface MoreDialogClickListener {
        fun clickDetail()
        fun clickStudio()
        fun clickReport()
    }
}