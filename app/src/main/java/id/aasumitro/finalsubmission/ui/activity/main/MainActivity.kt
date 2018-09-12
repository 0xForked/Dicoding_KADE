package id.aasumitro.finalsubmission.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_FIRST_INFO
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_NAME
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.STATUS_EVER
import id.aasumitro.finalsubmission.ui.dialog.setting.SettingDialog
import id.aasumitro.finalsubmission.ui.fragment.favorite.FavoriteFragment
import id.aasumitro.finalsubmission.ui.fragment.event.EventFragment
import id.aasumitro.finalsubmission.ui.fragment.team.TeamFragment
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_TEAM
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_VALUE
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import android.support.v4.os.ResultReceiver
import id.aasumitro.finalsubmission.ui.activity.search.SearchActivity
import id.aasumitro.finalsubmission.ui.activity.search.SearchActivity.Companion.INTENT_SEARCH
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    private var mRepository: Repository? = null
    private var mLeague: String? = null

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_event -> {
                supportActionBar?.title = resources.getString(R.string.title_event) + " (${mLeague ?: ""})"
                SEARCH_VALUE = SEARCH_MATCH
                replaceFragment(EventFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team -> {
                supportActionBar?.title = resources.getString(R.string.title_team) + " (${mLeague ?: ""})"
                SEARCH_VALUE = SEARCH_TEAM
                replaceFragment(TeamFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                replaceFragment(FavoriteFragment())
                supportActionBar?.title = resources.getString(R.string.title_favorite)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRepository = Repository(this)
        mLeague = mRepository?.getPrefs(KEY_LEAGUE_NAME)
        act_main_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        act_main_navigation.selectedItemId = R.id.navigation_event
        firstInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_search -> {
                startActivity<SearchActivity>(
                        INTENT_SEARCH to SEARCH_VALUE)
                true
            }
            R.id.menu_setting -> {
                val finish = Intent(this@MainActivity, SettingDialog::class.java)
                finish.putExtra("finisher", object : ResultReceiver(null) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
                        this@MainActivity.finish()
                    }
                })
                startActivityForResult(finish, 1)
                true
            }
            R.id.menu_about -> {
                aboutApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment (fragment: Fragment) {
        val manager = supportFragmentManager.beginTransaction()
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

    private fun firstInfo() {
       val first =
               mRepository?.getPrefs(KEY_FIRST_INFO)
       if (first != STATUS_EVER) {
           alert("Hello, thanks for using Real Football App, " +
                   "your default league has been set as $mLeague " +
                   "if you want to change it just go to: \n" +
                   "look at app toolbar -> menu -> setting",
                   "First Info!") {
               yesButton {
                   toast("ggwp real football")
               }
           }.show()
       }
       mRepository?.setPrefs(KEY_FIRST_INFO, STATUS_EVER)
    }

    private fun aboutApp() {
        alert("""
            App Version: 0.0.1
            Default League: English Premier League
            Current League: $mLeague
        """.trimIndent()) {
            yesButton {
                toast("ggwp real football")
            }
        }.show()
    }

}
