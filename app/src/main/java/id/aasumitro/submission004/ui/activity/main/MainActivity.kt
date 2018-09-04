package id.aasumitro.submission004.ui.activity.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import id.aasumitro.submission004.R
import id.aasumitro.submission004.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission004.util.AppConst.MATCH_VALUE
import id.aasumitro.submission004.util.AppConst.NEXT_MATCH
import id.aasumitro.submission004.util.AppConst.PREV_MATCH
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import id.aasumitro.submission004.ui.fragment.ListFragment

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.navigation_prev -> {
                        replaceFragment(ListFragment(), PREV_MATCH)
                        supportActionBar?.title = "${resources.getString(R.string.title_prev_match)} ${resources.getString(R.string.title_epl)}"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_next -> {
                        replaceFragment(ListFragment(), NEXT_MATCH)
                        supportActionBar?.title = "${resources.getString(R.string.title_next_match)} ${resources.getString(R.string.title_epl)}"
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.navigation_favorite -> {
                        replaceFragment(ListFragment(), FAVORITE_MATCH)
                        supportActionBar?.title = resources.getString(R.string.title_favorite_match)
                        return@OnNavigationItemSelectedListener true

                    }
                }
                false
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        act_main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        act_main_navigation.selectedItemId = R.id.navigation_prev
    }

    private fun replaceFragment (fragment: Fragment,
                                 message: String) {
        val manager = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString(MATCH_VALUE, message)
        fragment.arguments = bundle
        manager.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        manager.replace(R.id.act_main_fr_container, fragment)
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
