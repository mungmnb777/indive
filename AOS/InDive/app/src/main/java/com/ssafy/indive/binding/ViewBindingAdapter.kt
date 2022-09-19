//
//import android.widget.TextView
//import androidx.databinding.BindingAdapter
//import java.util.*
//
//
//object ViewBindingAdapter {
//
//    @BindingAdapter("startTime", "endTime")
//    @JvmStatic
//    fun TextView.setStartTime(timeStart: String, timeEnd: String){
//        val startToken = StringTokenizer(timeStart, ":")
//        val startHour = startToken.nextToken()
//        val startMinute = startToken.nextToken()
//
//        val endToken = StringTokenizer(timeEnd, ":")
//        val endHour = endToken.nextToken()
//        val endMinute = endToken.nextToken()
//
//        this.text = "$startHour:$startMinute ~ $endHour:$endMinute"
//    }
//
//
//}