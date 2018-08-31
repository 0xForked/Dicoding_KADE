package id.aasumitro.submission002.ui.main.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import id.aasumitro.submission002.R
import id.aasumitro.submission002.ui.main.fragment.MainFragment
import id.aasumitro.submission002.util.AppConst.MATCH_VALUE
import id.aasumitro.submission002.util.AppConst.NEXT_MATCH
import id.aasumitro.submission002.util.AppConst.PREV_MATCH
import id.aasumitro.submission002.util.AppConst.STATUS_EVER
import id.aasumitro.submission002.util.AppConst.STATUS_NEVER
import id.aasumitro.submission002.util.AppConst.TODAY_MATCH
import id.aasumitro.submission002.util.DataHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_prev -> {
                replaceFragment(MainFragment(), PREV_MATCH)
                supportActionBar?.title = "${resources.getString(R.string.title_prev_match)} ${resources.getString(R.string.title_epl)}"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_this -> {
                replaceFragment(MainFragment(), TODAY_MATCH)
                supportActionBar?.title = "${resources.getString(R.string.match_today)} ${resources.getString(R.string.title_epl)}"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                replaceFragment(MainFragment(), NEXT_MATCH)
                supportActionBar?.title = "${ resources.getString(R.string.title_next_match)} ${resources.getString(R.string.title_epl)}"
                return@OnNavigationItemSelectedListener true
            }
        }
         false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_this
        isFirstLaunch()
    }

    private fun isFirstLaunch() {
        val isFirst = DataHelper.isFirstLaunch(this)
        when (isFirst) {
            null -> DataHelper.setFirstLaunchStatus(this, STATUS_EVER)
            STATUS_NEVER -> DataHelper.setFirstLaunchStatus(this, STATUS_EVER)
            STATUS_EVER -> info { "FIRST LAUNCH : FALSE (NOT FIRST OPEN)" }
            else -> info { "VALUE NOT FOUND" }
        }
    }

    private fun replaceFragment (fragment: Fragment,
                                 message: String) {
        val manager = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString(MATCH_VALUE, message)
        fragment.arguments = bundle
        manager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        manager.replace(R.id.fragment_container, fragment)
        manager.commit()
    }

    override fun onBackPressed() {
        val manager = supportFragmentManager
        if (manager.backStackEntryCount > 1) {
            manager.popBackStack()
        } else {
            finish()
        }
    }

}
