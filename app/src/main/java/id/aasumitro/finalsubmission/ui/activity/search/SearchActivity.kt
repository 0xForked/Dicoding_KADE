package id.aasumitro.finalsubmission.ui.activity.search

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_TEAM
import id.aasumitro.finalsubmission.util.NetworkHelper
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import android.content.Intent
import android.provider.CalendarContract
import android.provider.Settings
import android.support.v7.widget.*
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity.Companion.INTENT_TEAM_EXTRA
import id.aasumitro.finalsubmission.ui.rv.adapter.MatchAdapter
import id.aasumitro.finalsubmission.ui.rv.adapter.TeamAdapter
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.ui.rv.listener.TeamListener
import id.aasumitro.finalsubmission.util.DateTimeHelper
import id.aasumitro.finalsubmission.util.GlobalConst.PAST_MATCH
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.yesButton
import java.util.*
import java.text.SimpleDateFormat


class SearchActivity : AppCompatActivity(), SearchNavigator, TeamListener, MatchListener {

    companion object {
        const val INTENT_SEARCH = "SEARCH"
    }

    private var mRepository: Repository? = null
    private var mSearchType: String? = null

    private val mViewModel: SearchViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val searchType = intent.getStringExtra(INTENT_SEARCH)
        mSearchType = searchType
        mRepository = Repository(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search ${searchType(searchType)}"
        mViewModel.initVM(this, mRepository as Repository)
        networkState()
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchView =
                menu?.findItem(R.id.searchMenu)?.actionView as SearchView
        val searchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnSearchClickListener { /*LoadData("null")*/ }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    if (query.isNotEmpty()) {
                        mViewModel.startTask(mSearchType, query)
                    }
                }
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    if (query.length > 1) {
                        mViewModel.startTask(mSearchType, query)
                    }
                }
                return false
            }
        })
        searchView.setOnCloseListener { false }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchType(type: String?): String? {
        return when (type) {
            SEARCH_TEAM -> "Team"
            SEARCH_MATCH -> "Match"
            else -> null
        }
    }

    private fun networkState() {
        val isConnected =
                NetworkHelper.isNetworkAvailable(this)
        if (!isConnected) {
            alert("Please enable network connection",
                    "Network issue!") {
                positiveButton("Enable") {
                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(intent)
                    toast("open network configuration")
                }
                negativeButton("Later") { finish() }
            }.show().setCancelable(false)
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            act_search_loading_layout.visible()
            act_search_match_rv.gone()
            act_search_team_rv.gone()
        } else {
            act_search_loading_layout.gone()
            when (mSearchType) {
                SEARCH_TEAM -> act_search_team_rv.visible()
                SEARCH_MATCH -> act_search_match_rv.visible()
            }
        }
    }

    private fun initRecyclerView() {
        when (mSearchType) {
            SEARCH_TEAM -> {
                act_search_team_rv.setHasFixedSize(true)
                val layoutManager : RecyclerView.LayoutManager =
                        GridLayoutManager(this, 3)
                act_search_team_rv.layoutManager = layoutManager
                act_search_team_rv.itemAnimator = DefaultItemAnimator()
            }
            SEARCH_MATCH -> {
                act_search_match_rv.setHasFixedSize(true)
                val layoutManager : RecyclerView.LayoutManager =
                        LinearLayoutManager(this)
                act_search_match_rv.layoutManager = layoutManager
                act_search_match_rv.itemAnimator = DefaultItemAnimator()
            }
        }
    }

    override fun initListAdapter() {
        when (mSearchType) {
            SEARCH_TEAM -> {
                act_search_team_rv.adapter =
                        TeamAdapter(mViewModel.mTeamData,
                                this)
            }
            SEARCH_MATCH -> {
                act_search_match_rv.adapter =
                        MatchAdapter(mViewModel.mMatchData,
                                this,
                                SEARCH_MATCH)
            }
        }
    }

    override fun clearRecyclerView() {
        when (mSearchType) {
            SEARCH_TEAM -> {
                act_search_team_rv.recycledViewPool.clear()
            }
            SEARCH_MATCH -> {
                act_search_match_rv.recycledViewPool.clear()
            }
        }
    }

    override fun onTeamPressed(team: Team?) {
        startActivity<TeamDetailsActivity>(
                INTENT_TEAM_EXTRA to team)
    }

    override fun onMatchPressed(match: Match?) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val strDate = sdf.parse(match?.date)
        if (System.currentTimeMillis() > strDate.time) {
            startActivity<MatchDetailActivity>(
                    MatchDetailActivity.INTENT_DATA_EVENT_ID to match?.uniqueId,
                    MatchDetailActivity.INTENT_MATCH_TYPE to PAST_MATCH)
        } else {
            alert("", "Remind me!") {
                yesButton {
                    remindMatch(match)
                }
            }.show()
        }

        // if (isYesterday(match) as Boolean) { } else { }

    }

    private fun remindMatch(match: Match?) {
        val description = "${match?.homeName} vs ${match?.awayName}"
        val year = DateTimeHelper.getYear(match?.date)?.toInt() as Int
        val month = DateTimeHelper.getMonth(match?.date)?.toInt() as Int
        val day = DateTimeHelper.getDay(match?.date)?.toInt() as Int
        val hour = DateTimeHelper.getHour(match?.time)?.toInt() as Int
        val minute = DateTimeHelper.getMinute(match?.time)?.toInt() as Int
        val beginTime = Calendar.getInstance()
        beginTime.set(year, month, day, hour, minute)
        val endTime = Calendar.getInstance()
        endTime.set(year, month, day, hour, minute)

        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.Events.TITLE, description)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
        intent.putExtra(CalendarContract.CalendarAlerts.ALARM_TIME, beginTime.timeInMillis)
        startActivity(intent)
    }

    private fun isYesterday(match: Match?): Boolean? {
        val year = DateTimeHelper.getYear(match?.date)?.toInt() as Int
        val month = DateTimeHelper.getMonth(match?.date)?.toInt() as Int
        val day = DateTimeHelper.getDay(match?.date)?.toInt() as Int
        val today = Calendar.getInstance()
        val matchDate = Calendar.getInstance()
        matchDate.set(year, month, day)
        if (matchDate.before(today))
        {
            return true
        }
        return false
    }

}
