package id.aasumitro.submission002.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission002.R
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.data.remote.ApiClient
import id.aasumitro.submission002.util.*
import id.aasumitro.submission002.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission002.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission002.util.AppConst.NEXT_MATCH
import id.aasumitro.submission002.util.AppConst.PREV_MATCH
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_error_layout.*
import id.aasumitro.submission002.util.StringHelper.implodeExplode
import id.aasumitro.submission002.util.StringHelper.splitString

class DetailActivity : AppCompatActivity(), DetailNavigator {

    // TODO (NEXT)
    // implement fragment for event detail, team detail & favorite list
    // Refactoring and clean up the code

    private val mApiClient = ApiClient()

    private val mViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.match_detail)
        val mEventId = intent.getStringExtra(INTENT_DATA_EVENT_ID)
        mViewModel.initVM(this, mApiClient)
        mViewModel.startTask(mEventId)
        liveDataObserver()
        onLayoutRefresh(mEventId)
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

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            detail_swipeRefreshLayout.visible()
            layout_error_detail.gone()
        } else {
            detail_swipeRefreshLayout.gone()
            layout_error_detail.visible()
            error_home_code.text = code
            error_home_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            layout_loading_detail.visible()
            detail_swipeRefreshLayout.gone()
            layout_error_detail.gone()
        } else {
            layout_loading_detail.gone()
            detail_swipeRefreshLayout.visible()
        }
    }

    override fun checkNetworkConnection() : Boolean? {
        return NetworkHelper.isNetworkAvailable(this)
    }

    private fun onLayoutRefresh(eventId :String) {
        detail_swipeRefreshLayout.setOnRefreshListener {
            detail_swipeRefreshLayout.isRefreshing = false
            mViewModel.startTask(eventId)
            liveDataObserver()
        }
    }

    private fun liveDataObserver() {
        mViewModel.getDataResult().observe(this, Observer {
            bindData(it)
        })
    }

    private fun bindData(match: Match?) {
        detail_match_date_time.text = getEventDatetime(match?.eventDate, match?.eventTime)
        detail_match_stadium.text = DataHelper.getTeamStadium(this, match?.homeId)

        detail_team_home_name.text = match?.homeName
        detail_team_home_score.text = match?.homeScore
        detail_team_home_formation.text = match?.homeFormation

        getTeamBandage(match?.homeId, match?.awayId)

        detail_team_away_name.text = match?.awayName
        detail_team_away_score.text = match?.awayScore
        detail_team_away_formation.text = match?.awayFormation


        if (GLOBAL_MATCH_VALUE === PREV_MATCH) {
            val homeScorer = implodeExplode(match?.homeGoalDetails)
            val awayScorer = implodeExplode(match?.awayGoalDetails)
            detail_team_home_scorer.text = homeScorer
            detail_team_away_scorer.text = awayScorer

            if (match?.homeFormation == null && match?.awayFormation == null ||
                    match.homeFormation.equals("") && match.awayFormation.equals("")) {
                detail_team_home_formation.text =
                        getEstimateFormation(
                                match?.homeLineupDefense,
                                match?.homeLineupMidfield,
                                match?.homeLineupForward)
                detail_team_away_formation.text =
                        getEstimateFormation(
                                match?.awayLineupDefense,
                                match?.awayLineupMidfield,
                                match?.awayLineupForward)
            }

            detail_team_home_goalkeeper.text = implodeExplode(match?.homeLineupGoalkeeper)
            detail_team_home_defense.text = implodeExplode(match?.homeLineupDefense)
            detail_team_home_midfield.text = implodeExplode(match?.homeLineupMidfield)
            detail_team_home_forward.text = implodeExplode(match?.homeLineupForward)
            detail_team_home_substitutes.text = implodeExplode(match?.homeLineupSubstitutes)

            detail_team_away_goalkeeper.text = implodeExplode(match?.awayLineupGoalkeeper)
            detail_team_away_defense.text = implodeExplode(match?.awayLineupDefense)
            detail_team_away_midfield.text = implodeExplode(match?.awayLineupMidfield)
            detail_team_away_forward.text = implodeExplode(match?.awayLineupForward)
            detail_team_away_substitutes.text = implodeExplode(match?.awayLineupSubstitutes)
        }

        if (GLOBAL_MATCH_VALUE === NEXT_MATCH) {
            next_info.visible()
            layout_goals.gone()
            div_header_goals.gone()
            div_goals_lineups.gone()
            layout_lineup.gone()
        }

        if (match?.homeScore?.toInt() == 0 && match.awayScore?.toInt() == 0) {
            layout_goals.gone()
        }

    }

    private fun getEstimateFormation(defenseLineup: String?,
                             midfieldLineup: String?,
                             forwardLineup: String?): String {
        val defense = splitString(defenseLineup).size - 1
        val midfield = splitString(midfieldLineup).size - 1
        val forward = splitString(forwardLineup).size - 1
        return "$defense-$midfield-$forward *guess"
    }

    private fun getEventDatetime(date: String?, time: String?): String? {
        val eventDate = DateTime().reformatDate(date)
        val eventTime = DateTime().reformatTime(time)
        return "$eventDate - $eventTime"
    }

    private fun getTeamBandage(homeId: String?, awayId: String?) {
        Picasso.get()
                .load(DataHelper.getTeamBandage(this, homeId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(detail_team_home_bandage)

        Picasso.get()
                .load(DataHelper.getTeamBandage(this, awayId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(detail_team_away_bandage)

    }

}
