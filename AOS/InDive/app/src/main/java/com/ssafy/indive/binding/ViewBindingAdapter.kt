package com.ssafy.indive.binding


import com.ssafy.indive.utils.*
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.indive.R
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat
import java.text.NumberFormat

object ViewBindingAdapter {

    @BindingAdapter("setImageCircle")
    @JvmStatic
    fun CircleImageView.bindImage(seq: Long) {
        Glide.with(this.context).load("${MEMBER_HEADER}$seq${MEMBER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image

            ).skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)
        Log.d("d102", "res: ${MEMBER_HEADER}$seq${MEMBER_FOOTER}")
    }

    @BindingAdapter("bindMusicImage")
    @JvmStatic
    fun ImageView.bindMusicImage(seq: Long) {
        Glide.with(this.context).load("${COVER_HEADER}$seq${COVER_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image
            ).into(this)

    }

    @BindingAdapter("bindBackImage")
    @JvmStatic
    fun ImageView.bindBackImage(seq: Long) {
        Glide.with(this.context).load("${BACKGROUND_HEADER}$seq${BACKGROUND_FOOTER}").centerCrop()
            .placeholder(
                R.drawable.album_default_image
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)

    }

    @BindingAdapter("bindIVEText")
    @JvmStatic
    fun TextView.bindIVEText(quantity: String){
        val formatter: NumberFormat = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(quantity.toInt())
        this.setText(formattedNumber)
    }
}