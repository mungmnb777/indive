package com.ssafy.indive.utils

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.ssafy.indive.R
import java.text.SimpleDateFormat
import java.util.*

// 다이얼로그 사이즈 조절
fun Context.dialogResize(dialogFragment: DialogFragment, width: Float, height: Float) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    val display = windowManager.defaultDisplay
    val size = Point()

    display.getSize(size)

    val params: ViewGroup.LayoutParams? = dialogFragment.dialog?.window?.attributes
    val deviceWidth = size.x
    val deviceHeight = size.y

    params?.width = (deviceWidth * width).toInt()
    params?.height = (deviceHeight * height).toInt()
    dialogFragment.dialog?.window?.attributes = params as WindowManager.LayoutParams
    dialogFragment.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

}

// 서버 시간 포매터
fun timeFormatter(time: Long?): String {
    if (time == null) {
        return ""
    }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    return dateFormat.format(time)
}

// 러닝 제목 포매터
fun timeNameFormatter(time: Long?): String {
    if (time == null) {
        return ""
    }
    val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일 러닝")

    return dateFormat.format(time)
}


