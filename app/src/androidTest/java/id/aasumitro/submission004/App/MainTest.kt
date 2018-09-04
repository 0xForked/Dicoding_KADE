package id.aasumitro.submission004.App

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.aasumitro.submission004.R.id.*
import id.aasumitro.submission004.ui.activity.main.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainTest {

    @Rule
    @JvmField var activityMainRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testMarkAsFavorite() {

        // mulai

        // pertama cek apa ada, "toast success init list"
        // dan recycler view (list dari favorite) ditampilkan
        // scroll dan pilih salah satu
        onView(withText("success init list"))
                .inRoot(withDecorView(not(activityMainRule
                        .activity
                        .window
                        .decorView
                )))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())
        Thread.sleep(2000)
        onView(withId(navigation_prev)).perform(click())
        onView(withText("success init list"))
                .inRoot(withDecorView(not(activityMainRule
                        .activity
                        .window
                        .decorView
                )))
                .check(matches(isDisplayed()))
        onView(withId(fr_main_rv))
                .check(matches(isDisplayed()))
                .perform(
                        RecyclerViewActions
                                .scrollToPosition
                                <RecyclerView.ViewHolder>
                                (10), click())

        // pindah ke activity detail
        // tahan 3 detik untuk mengambil data
        // pilih menu favorite untuk mengatur favorite match
        Thread.sleep(3000)
        onView(withId(menu_favorite))
                .check(matches(isDisplayed()))
                .perform(click())

        // setelah mark sebagai favorite tunggu 1 detik
        // dan tekan tombol balik
        Thread.sleep(1000)
        pressBack()

        // pada main activity pastikan apakah bottomNavigation
        // ditampilkan, apabila ditampilkan cari dan klik menu
        // favorite tahan selama 2 detik untuk melihat data
        onView(withId(act_main_navigation))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorite))
                .perform(click())
        Thread.sleep(2000)

        // selesai

    }

    @Test
    fun testRemoveFromFavorite() {
        // ket: data favorite harus sudah ada.
        // mulai

        // pertama cek bottom navigation ditampilkan atau tidak
        // apabila ditampilkan klik pada navigasi menu favorite
        onView(withId(act_main_navigation))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorite)).perform(click())

        // setelah klik favorite ketika muncul "toast success init list"
        // dan recycler view (list dari favorite) ditampilkan
        // pilih salah satu
        onView(withText("success init list"))
                .inRoot(withDecorView(not(activityMainRule
                        .activity
                        .window
                        .decorView
                )))
                .check(matches(isDisplayed()))
        onView(withId(fr_main_rv))
                .check(matches(isDisplayed()))
                .perform(
                        RecyclerViewActions
                                .scrollToPosition
                                <RecyclerView.ViewHolder>
                                (1), click())

        // setelah di pilih salah satu dari list sebelumnya
        // maka otomatis view akan beralih detail activity
        // di detail activity app akan menekan menu favorite
        // untuk menghapus favorite data setelah 3 detik
        Thread.sleep(3000)
        onView(withId(menu_favorite))
                .check(matches(isDisplayed()))
                .perform(click())

        // ketika favorite data berhasil di hapus
        // app akan kembali ke main activity
        Thread.sleep(1000)
        pressBack()

        //selesai

    }

}
