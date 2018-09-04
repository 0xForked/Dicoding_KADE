package id.aasumitro.submission004.util

import id.aasumitro.submission004.util.DateTime.reformatDate
import id.aasumitro.submission004.util.DateTime.reformatTime
import org.junit.Test

import org.junit.Assert.*

class DateTimeTest {

    @Test
    fun testReformatDate() {
        val date = "2018-09-03"
        assertEquals("Mon, Sep 2018", reformatDate(date))
    }

    @Test
    fun testReformatTime() {
        val time = "12:30:00+00:00"
        assertEquals("12:30", reformatTime(time))
    }

}