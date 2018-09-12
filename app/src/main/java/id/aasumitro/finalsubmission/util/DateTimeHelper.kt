package id.aasumitro.finalsubmission.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHelper {

    private const val SERVER_DATE_PATTERN = "yyyy-MM-dd"
    private const val APP_DATE_PATTERN = "EEE, dd MMM yyy"
    private const val APP_TIME_PATTERN = "hh:mm"
    private const val YEAR = "yyy"
    private const val MONTH = "MM"
    private const val DAY = "dd"
    private const val HOUR = "hh"
    private const val MINUTE = "mm"
    private var timeFormat = Arrays.asList("HH:mm:ss", "HH:mm:ss+SS:SS")

    private fun getTimeGMTFormat(time: String?): Date? {
        for (formatString in timeFormat) {
            val formatter =  SimpleDateFormat(formatString, Locale.ENGLISH)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter.parse(time)
        }
        return null
    }

    private fun getDateGMTFormat(date: String?): Date? {
        val formatter = SimpleDateFormat(SERVER_DATE_PATTERN, Locale.ENGLISH)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.parse(date)
    }

    fun reformatDate(date:String?): String? {
        val originDate = getDateGMTFormat(date)
        val dateFormat = SimpleDateFormat(APP_DATE_PATTERN, Locale.ENGLISH)
        return dateFormat.format(originDate)
    }

    fun reformatTime(time: String?): String? {
        val originTime = getTimeGMTFormat(time)
        val timeFormat = SimpleDateFormat(APP_TIME_PATTERN, Locale.ENGLISH)
        return timeFormat.format(originTime)
    }

    fun getYear(date: String?): String? {
        val originDate = getDateGMTFormat(date)
        val dateFormat = SimpleDateFormat(YEAR, Locale.ENGLISH)
        return dateFormat.format(originDate)
    }

    fun getMonth(date: String?): String? {
        val originDate = getDateGMTFormat(date)
        val dateFormat = SimpleDateFormat(MONTH, Locale.ENGLISH)
        return dateFormat.format(originDate)
    }

    fun getDay(date: String?): String? {
        val originDate = getDateGMTFormat(date)
        val dateFormat = SimpleDateFormat(DAY, Locale.ENGLISH)
        return dateFormat.format(originDate)
    }

    fun getHour(time: String?): String? {
        val originTime = getTimeGMTFormat(time)
        val timeFormat = SimpleDateFormat(HOUR, Locale.ENGLISH)
        return timeFormat.format(originTime)
    }

    fun getMinute(time: String?): String? {
        val originTime = getTimeGMTFormat(time)
        val timeFormat = SimpleDateFormat(MINUTE, Locale.ENGLISH)
        return timeFormat.format(originTime)
    }

}