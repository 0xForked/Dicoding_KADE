package id.aasumitro.submission002.util

import id.aasumitro.submission002.util.AppConst.APP_DATE_PATTERN
import id.aasumitro.submission002.util.AppConst.APP_TIME_PATTERN
import id.aasumitro.submission002.util.AppConst.SERVER_DATE_PATTERN
import id.aasumitro.submission002.util.AppConst.SERVER_TIME_PATTERN
import java.text.SimpleDateFormat
import java.util.*

class DateTime {

    fun thisTime() : String {
        val dateFormat = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.ENGLISH)
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun reformatDate(date:String?) : String {
        var dateFormat = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.ENGLISH)
        val originDate = dateFormat.parse(date)
        dateFormat = SimpleDateFormat(APP_DATE_PATTERN, Locale.ENGLISH)
        return dateFormat.format(originDate)
    }

    fun reformatTime(time: String?) : String {
        var timeFormat = SimpleDateFormat(SERVER_TIME_PATTERN, Locale.ENGLISH)
        val originTime = timeFormat.parse(time)
        timeFormat = SimpleDateFormat(APP_TIME_PATTERN, Locale.ENGLISH)
        return timeFormat.format(originTime)
    }

}