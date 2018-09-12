package id.aasumitro.finalsubmission.ui.activity.detail.match

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.util.BitmapTransform
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_HEIGHT
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_WIDTH
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.SIZE
import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatDate
import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatTime
import id.aasumitro.finalsubmission.util.GlobalConst.GLOBAL_MATCH_VALUE
import id.aasumitro.finalsubmission.util.GlobalConst.NEXT_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.PAST_MATCH
import id.aasumitro.finalsubmission.util.NetworkHelper.isNetworkAvailable
import id.aasumitro.finalsubmission.util.StringHelper.implodeExplode
import id.aasumitro.finalsubmission.util.StringHelper.splitString
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.item_detail_header.*
import kotlinx.android.synthetic.main.item_detail_lineup.*
import kotlinx.android.synthetic.main.item_detail_scorer.*
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.toast

class MatchDetailActivity : AppCompatActivity(), MatchDetailNavigator {

    private var mRepository: Repository? = null

    private var mMenuItem: Menu? = null

    private var isFavoriteMatch: Boolean = false
    private var isLoadingNow: Boolean = false

    private var mEventId: String? = null
    private var mMatchType: String? = null
    private var mMatch: Match? = null

    companion object {
        const val INTENT_DATA_EVENT_ID = "INTENT_EVENT_ID"
        const val INTENT_MATCH_TYPE = "INTENT_MATCH_TYPE"
    }

    private val mViewModel: MatchDetailViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(MatchDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.match_detail)
        mRepository = Repository(this)
        mEventId = intent.getStringExtra(INTENT_DATA_EVENT_ID)
        mMatchType = intent.getStringExtra(INTENT_MATCH_TYPE)
        mViewModel.initVM(this, mRepository as Repository)
        mViewModel.startTask(mEventId)
        liveDataObserver()
        onLayoutRefresh()
        favoriteState()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        mMenuItem = menu
        favoriteStatus()
        menu?.findItem(R.id.menu_favorite)?.isVisible = isLoadingNow
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_favorite -> {
                if (isFavoriteMatch) {
                    mViewModel.removeFromFavorite(mEventId?.toInt())
                } else {
                    mViewModel.markAsFavorite(mMatch, mMatchType)
                }
                isFavoriteMatch = !isFavoriteMatch
                favoriteStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        mViewModel
                .favoriteState(mEventId?.toInt())
        isFavoriteMatch =
                mViewModel.getFavoriteState()
    }

    private fun favoriteStatus() {
        if (isFavoriteMatch) {
            mMenuItem?.getItem(0)?.icon = ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite)
        } else {
            mMenuItem?.getItem(0)?.icon = ContextCompat
                    .getDrawable(this, R.drawable.ic_favorite_border)
        }
    }

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            act_detail_match_refresh_layout.visible()
            act_detail_match_error_layout.gone()
        } else {
            act_detail_match_refresh_layout.gone()
            act_detail_match_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            act_detail_match_loading_layout.visible()
            act_detail_match_refresh_layout.gone()
            act_detail_match_error_layout.gone()
            mMenuItem?.getItem(0)?.isVisible = false
        } else {
            act_detail_match_loading_layout.gone()
            act_detail_match_refresh_layout.visible()
            mMenuItem?.getItem(0)?.isVisible = true
        }
        isLoadingNow = isLoading
    }

    override fun onNextMatch() {
        act_detail_match_scorer_layout.gone()
        act_detail_match_lineup_layout.gone()
    }

    override fun makeToast(message: String?): Toast? = toast(message as String)

    override fun checkNetworkConnection() : Boolean? = isNetworkAvailable(this)

    private fun onLayoutRefresh() {
        act_detail_match_refresh_layout.setOnRefreshListener {
            act_detail_match_refresh_layout.isRefreshing = false
            mViewModel.startTask(mEventId)
            liveDataObserver()
        }
    }

    private fun liveDataObserver() {
        mViewModel.getDataResult()
                .observe(this, Observer {
                    bindData(it)
                    mMatch = it
                })
    }

    private fun bindData(match: Match?) {
        act_detail_match_txt_event_id.text = match?.uniqueId
        item_detail_txt_match_date_time.text = getEventDatetime(match?.date, match?.time)
        item_detail_txt_match_stadium.text = mRepository?.getTeamStadium(match?.homeId)
        item_detail_txt_home_name.text = match?.homeName
        item_detail_txt_home_score.text = match?.homeScore
        item_detail_txt_home_formation.text = match?.homeFormation
        getTeamBandage(match?.homeId, match?.awayId)
        item_detail_txt_away_name.text = match?.awayName
        item_detail_txt_away_score.text = match?.awayScore
        item_detail_txt_away_formation.text = match?.awayFormation

        if (match?.homeGoalDetails != null) {
            val homeScorer = implodeExplode(match.homeGoalDetails, false)
            item_detail_txt_home_scorer.text = homeScorer
        }

        if (match?.awayGoalDetails != null) {
            val awayScorer = implodeExplode(match.awayGoalDetails, false)
            item_detail_txt_away_scorer.text = awayScorer
        }

        if (match?.homeFormation == null && match?.awayFormation == null ||
                match.homeFormation.equals("") && match.awayFormation.equals("")) {
            item_detail_txt_home_formation.text =
                    getEstimateFormation(
                            match?.homeLineupDefense,
                            match?.homeLineupMidfield,
                            match?.homeLineupForward)
            item_detail_txt_away_formation.text =
                    getEstimateFormation(
                            match?.awayLineupDefense,
                            match?.awayLineupMidfield,
                            match?.awayLineupForward)
        }
        item_detail_txt_home_goalkeeper.text = implodeExplode(match?.homeLineupGoalkeeper, true)
        item_detail_txt_home_defense.text = implodeExplode(match?.homeLineupDefense, true)
        item_detail_txt_home_midfield.text = implodeExplode(match?.homeLineupMidfield, true)
        item_detail_txt_home_forward.text = implodeExplode(match?.homeLineupForward, true)
        item_detail_txt_home_substitutes.text = implodeExplode(match?.homeLineupSubstitutes, true)
        item_detail_txt_away_goalkeeper.text = implodeExplode(match?.awayLineupGoalkeeper, true)
        item_detail_txt_away_defense.text = implodeExplode(match?.awayLineupDefense, true)
        item_detail_txt_away_midfield.text = implodeExplode(match?.awayLineupMidfield, true)
        item_detail_txt_away_forward.text = implodeExplode(match?.awayLineupForward, true)
        item_detail_txt_away_substitutes.text = implodeExplode(match?.awayLineupSubstitutes, true)

        if (GLOBAL_MATCH_VALUE === NEXT_MATCH) {
            text_not_found.visible()
        }

    }

    private fun getEstimateFormation(defenseLineup: String?,
                                     midfieldLineup: String?,
                                     forwardLineup: String?): String {
        val defense = splitString(defenseLineup)?.size?.minus(1)
        val midfield = splitString(midfieldLineup)?.size?.minus(1)
        val forward = splitString(forwardLineup)?.size?.minus(1)
        return "$defense-$midfield-$forward *guess"
    }

    private fun getEventDatetime(date: String?, time: String?): String? {
        val eventDate = reformatDate(date)
        val eventTime = reformatTime(time)
        return "$eventDate - $eventTime"
    }

    private fun getTeamBandage(homeId: String?, awayId: String?) {
        val homeBandage =
                mRepository?.getTeamBandage(homeId)
        val awayBandage =
                mRepository?.getTeamBandage(awayId)
        Picasso.get()
                .load(homeBandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(SIZE, SIZE)
                .centerInside()
                .into(item_detail_img_home_bandage)
        Picasso.get()
                .load(awayBandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(SIZE, SIZE)
                .centerInside()
                .into(item_detail_img_away_bandage)
    }

}
