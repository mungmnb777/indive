package com.ssafy.indive.view.mystudio.addsong

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.GridLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.indive.databinding.DialogGenreBinding

class GenreDialog(var mcontext:Context): DialogFragment() {
    private lateinit var binding: DialogGenreBinding
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        genreList()
    }

    private fun initView(){
        genreAdapter = GenreAdapter()
        genreAdapter.onClickGenreItem = object: GenreAdapter.OnClickGenreListener{
            override fun onClick(view: View, position: Int, genre: String) {
                onDismissDialogListener.onDismiss(genre)
                dismiss()
            }
        }

        binding.apply {
            rvGenre.apply {
                adapter = genreAdapter
                layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        resizeDialog()
    }

    private fun resizeDialog() {
        val windowManager = mcontext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        val deviceHeight = size.y
        params?.width = (deviceWidth * 0.8).toInt()
        params?.height = (deviceHeight * 0.5).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    lateinit var onDismissDialogListener: OnDismissDialogListener
    interface OnDismissDialogListener{
        fun onDismiss(Genre: String)
    }

    private fun genreList(){
        genreAdapter.genreList.add("랩/힙합")
        genreAdapter.genreList.add("발라드")
        genreAdapter.genreList.add("연주곡")
        genreAdapter.genreList.add("R&B")
        genreAdapter.genreList.add("재즈")
        genreAdapter.genreList.add("댄스")
        genreAdapter.genreList.add("포크/블루스")
        genreAdapter.genreList.add("어쿠스틱")
        genreAdapter.genreList.add("락/메탈")
        genreAdapter.genreList.add("락발라드")
        genreAdapter.genreList.add("EDM")
        genreAdapter.genreList.add("트로트")
    }
}