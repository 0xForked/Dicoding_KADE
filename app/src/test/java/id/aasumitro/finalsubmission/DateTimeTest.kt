package id.aasumitro.finalsubmission

import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatDate
import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatTime
import org.junit.Test

import org.junit.Assert.*

class DateTimeTest {

    @Test fun testReformatDate() {
        val date = "2018-09-03"
        assertEquals("Mon, 03 Sep 2018", reformatDate(date))
    }

    @Test fun testReformatTime() {
        val time = "12:30:00+00:00"
        assertEquals("12:30", reformatTime(time))
    }

}