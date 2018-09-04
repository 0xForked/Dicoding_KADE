package id.aasumitro.submission004.ui.activity.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission004.R
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.data.sources.remote.ApiClient
import id.aasumitro.submission004.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission004.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission004.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission004.util.AppConst.MAX_HEIGHT
import id.aasumitro.submission004.util.AppConst.MAX_WIDTH
import id.aasumitro.submission004.util.AppConst.PREV_MATCH
import id.aasumitro.submission004.util.AppConst.SIZE
import id.aasumitro.submission004.util.BitmapTransform
import id.aasumitro.submission004.util.DateTime.reformatDate
import id.aasumitro.submission004.util.DateTime.reformatTime
import id.aasumitro.submission004.util.NetworkHelper.isNetworkAvailable
import id.aasumitro.submission004.util.StringHelper.implodeExplode
import id.aasumitro.submission004.util.StringHelper.splitString
import id.aasumitro.submission004.util.gone
import id.aasumitro.submission004.util.visible
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_detail_header.*
import kotlinx.android.synthetic.main.item_detail_lineup.*
import kotlinx.android.synthetic.main.item_detail_scorer.*
import kotlinx.android.synthetic.main.item_error_layout.*
import android.support.multidex.MultiDex
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.util.AppConst.NEXT_MATCH
import org.jetbrains.anko.toast


class DetailActivity : AppCompatActivity(), DetailNavigator {

    private val mApiClient = ApiClient()
    private var mRepository: EventRepository? = null

    private var mMenuItem: Menu? = null
    private var isFavoriteMatch: Boolean= false

    private var mEventId: String? = null
    private var mEventDate: String? = null
    private var mEventTime: String? = null

    private var mEventHomeId: String? = null
    private var mEventHomeName: String? = null
    private var mEventHomeScore: String? = null
    private var mEventAwayId: String? = null
    private var mEventAwayName: String? = null
    private var mEventAwayScore: String? = null

    private val mViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.match_detail)
        mRepository = EventRepository(this, mApiClient)
        mEventId = intent.getStringExtra(INTENT_DATA_EVENT_ID)
        mViewModel.initVM(this, mRepository as EventRepository, mApiClient)
        mViewModel.startTask(mEventId)
        liveDataObserver()
        onLayoutRefresh()
        favoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        mMenuItem = menu
        favoriteStatus()
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
                    val homeBandage =
                            mRepository?.getTeamBandage(mEventHomeId)
                    val awayBandage =
                            mRepository?.getTeamBandage(mEventAwayId)
                    val favorite = Favorite(
                            null,
                            mEventId,
                            mEventDate,
                            mEventTime,
                            mEventHomeId,
                            mEventHomeName,
                            mEventHomeScore,
                            homeBandage,
                            mEventAwayId,
                            mEventAwayName,
                            mEventAwayScore,
                            awayBandage)
                    mViewModel.markAsFavorite(favorite)
                }
                isFavoriteMatch = !isFavoriteMatch
                favoriteStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        mViewModel.favoriteState(mEventId?.toInt())
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
            act_detail_refresh_layout.visible()
            act_detail_error_layout.gone()
            mMenuItem?.getItem(0)?.isVisible = true
        } else {
            act_detail_refresh_layout.gone()
            act_detail_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
            mMenuItem?.getItem(0)?.isVisible = false
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            act_detail_loading_layout.visible()
            act_detail_refresh_layout.gone()
            act_detail_error_layout.gone()
            mMenuItem?.getItem(0)?.isVisible = false
        } else {
            act_detail_loading_layout.gone()
            act_detail_refresh_layout.visible()
            mMenuItem?.getItem(0)?.isVisible = true
        }
    }

    override fun onNextMatch() {
        act_detail_scorer_layout.gone()
        act_detail_lineup_layout.gone()
    }

    override fun showToast(message: String?) = toast(message as String)

    override fun checkNetworkConnection() : Boolean? = isNetworkAvailable(this)

    private fun onLayoutRefresh() {
        act_detail_refresh_layout.setOnRefreshListener {
            act_detail_refresh_layout.isRefreshing = false
            mViewModel.startTask(mEventId)
            liveDataObserver()
        }
    }

    private fun liveDataObserver() {
        mViewModel.getDataResult()
                .observe(this, Observer {
            bindData(it)
        })
    }

    private fun bindData(match: Match?) {
        mEventDate = match?.eventDate
        mEventTime = match?.eventTime
        mEventHomeId = match?.homeId
        mEventHomeName = match?.homeName
        mEventHomeScore = match?.homeScore
        mEventAwayId = match?.awayId
        mEventAwayName = match?.awayName
        mEventAwayScore = match?.awayScore

        act_detail_txt_event_id.text = match?.eventId
        item_detail_txt_match_date_time.text = getEventDatetime(match?.eventDate, match?.eventTime)
        item_detail_txt_match_stadium.text = mRepository?.getTeamStadium(match?.homeId)
        item_detail_txt_home_name.text = match?.homeName
        item_detail_txt_home_score.text = match?.homeScore
        item_detail_txt_home_formation.text = match?.homeFormation
        getTeamBandage(match?.homeId, match?.awayId)
        item_detail_txt_away_name.text = match?.awayName
        item_detail_txt_away_score.text = match?.awayScore
        item_detail_txt_away_formation.text = match?.awayFormation
        if (GLOBAL_MATCH_VALUE === PREV_MATCH || GLOBAL_MATCH_VALUE === FAVORITE_MATCH) {
            val homeScorer = implodeExplode(match?.homeGoalDetails, false)
            val awayScorer = implodeExplode(match?.awayGoalDetails, false)
            item_detail_txt_home_scorer.text = homeScorer
            item_detail_txt_away_scorer.text = awayScorer
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
        }

        if (GLOBAL_MATCH_VALUE === NEXT_MATCH) {
            text_not_found.visible()
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
