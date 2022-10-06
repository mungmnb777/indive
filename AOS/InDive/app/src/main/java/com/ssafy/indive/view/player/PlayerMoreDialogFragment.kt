package com.ssafy.indive.view.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.ssafy.indive.R
import com.ssafy.indive.databinding.FragmentPlayerMoreDialogBinding

class PlayerMoreDialogFragment(
    private val moreDialogClickListener: PlayerMoreDialogClickListener
) : DialogFragment() {

    private lateinit var binding: FragmentPlayerMoreDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_more_dialog, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvGoDetail.setOnClickListener {
            moreDialogClickListener.clickDetail()
            dismiss()
        }

        binding.cvReport.setOnClickListener {
            moreDialogClickListener.clickReport()
        }

    }

    interface PlayerMoreDialogClickListener {
        fun clickDetail()
        fun clickReport()
    }
}