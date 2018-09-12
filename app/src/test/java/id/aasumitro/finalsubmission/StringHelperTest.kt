package id.aasumitro.finalsubmission

import id.aasumitro.finalsubmission.util.StringHelper.implodeExplode
import id.aasumitro.finalsubmission.util.StringHelper.splitString
import org.junit.Test

import org.junit.Assert.*

class StringHelperTest {

    @Test fun testSplitString() {
        val text = "aku;kamu;dia"
        val array = listOf( "aku", "kamu", "dia")
        assertEquals(array, splitString(text))
    }

    @Test fun testImplodeExplode() {
        val text = "aku;kamu;dia"
        val textSpace = "aku; kamu; dia"
        val textExpected = "aku\nkamu\ndia"
        assertEquals(textExpected, implodeExplode(text, false))
        assertEquals(textExpected, implodeExplode(textSpace, true))
    }

}