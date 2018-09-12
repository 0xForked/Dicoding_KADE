package id.aasumitro.finalsubmission

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.aasumitro.finalsubmission.R.id.*
import id.aasumitro.finalsubmission.ui.activity.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MarkTeamFavorite {

    @Rule
    @JvmField var activityMainRule = ActivityTestRule(MainActivity::class.java)

    @Test fun test_MarkTeam_favorite() {
        Thread.sleep(4000)
        onView(withId(navigation_favorite)).perform(click())
        Thread.sleep(4000)
        onView(withId(navigation_team)).perform(click())
        onView(withId(fr_team_rv))
                .check(matches(isDisplayed()))
                .perform(
                        RecyclerViewActions
                                .scrollToPosition
                                <RecyclerView.ViewHolder>
                                (10), click())
        Thread.sleep(4000)
        onView(withId(menu_favorite))
                .check(matches(isDisplayed()))
                .perform(click())
        Thread.sleep(1000)
        pressBack()
        onView(withId(act_main_navigation))
                .check(matches(isDisplayed()))
        onView(withId(navigation_favorite))
                .perform(click())
        Thread.sleep(2000)
    }


}