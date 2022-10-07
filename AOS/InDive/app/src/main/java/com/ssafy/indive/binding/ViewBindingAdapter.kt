package com.ssafy.indive.binding


import android.graphics.Color
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
import java.text.SimpleDateFormat
import java.util.*

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
    fun TextView.bindIVEText(quantity: Int){
        val formatter: NumberFormat = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(quantity)
        this.setText(formattedNumber + " IVE")
    }

    @BindingAdapter("bindIVEText", "state")
    @JvmStatic
    fun TextView.bindIVEText(quantity: Int, state: String){
        val formatter: NumberFormat = DecimalFormat("#,###")
        val formattedNumber: String = formatter.format(quantity)
        when(state){
            "Send" ->  {
                this.setText("-" + formattedNumber + " IVE")
                this.setTextColor(resources.getColor(R.color.main_grey))
            }
            else -> {
                this.setText(formattedNumber + " IVE")
                this.setTextColor(resources.getColor(R.color.main_blue))
            }
        }
    }

    @BindingAdapter("bindStateText")
    @JvmStatic
    fun TextView.bindStateText(state: String) {
        val convertedState = when(state){
            "Send" -> "출금"
            else -> "입금"
        }
        this.setText(convertedState)
    }

    @BindingAdapter("bindDateText")
    @JvmStatic
    fun TextView.bindDateText(time: Long){
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val formattedDate = formatter.format(Date(time))

        this.setText(formattedDate)
    }

    @BindingAdapter("bindTokenImage")
    @JvmStatic
    fun ImageView.bindTokenImage(uri: String) {
        Glide.with(this.context).load("$BASE_URL/nft/$uri")
            .placeholder(
                R.drawable.album_default_image
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(this)

    }
}