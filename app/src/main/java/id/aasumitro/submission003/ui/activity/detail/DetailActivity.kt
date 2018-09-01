package id.aasumitro.submission003.ui.activity.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission003.R
import id.aasumitro.submission003.data.model.Match
import id.aasumitro.submission003.data.source.remote.ApiClient
import id.aasumitro.submission003.util.*
import id.aasumitro.submission003.util.AppConst.FAVORITE_MATCH
import id.aasumitro.submission003.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission003.util.AppConst.INTENT_DATA_EVENT_ID
import id.aasumitro.submission003.util.AppConst.PREV_MATCH
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_error_layout.*
import id.aasumitro.submission003.util.StringHelper.implodeExplode
import id.aasumitro.submission003.util.StringHelper.splitString
import kotlinx.android.synthetic.main.item_detail_header.*
import kotlinx.android.synthetic.main.item_detail_lineup.*
import kotlinx.android.synthetic.main.item_detail_scorer.*

class DetailActivity : AppCompatActivity(), DetailNavigator {

    private val mApiClient = ApiClient()
    private var isFavoriteMatch: Boolean= false
    private var mMatch: Match? = null
    private var mEventId: String? = null
    private var mMenuItem: Menu? = null

    private val mViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.match_detail)
        mEventId = intent.getStringExtra(INTENT_DATA_EVENT_ID)
        mViewModel.initVM(this, mApiClient)
        mViewModel.startTask(mEventId)
        liveDataObserver()
        onLayoutRefresh(mEventId)
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
                    mViewModel.removeFromFavorite(this, mEventId)
                } else {
                    mViewModel.markAsFavorite(this, mMatch)
                }
                isFavoriteMatch = !isFavoriteMatch
                favoriteStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        mViewModel.favoriteState(this, mEventId)
        isFavoriteMatch = mViewModel.getFavoriteState()
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
        } else {
            act_detail_refresh_layout.gone()
            act_detail_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            act_detail_loading_layout.visible()
            act_detail_refresh_layout.gone()
            act_detail_error_layout.gone()
        } else {
            act_detail_loading_layout.gone()
            act_detail_refresh_layout.visible()
        }
    }

    override fun onNextMatch() {
        act_detail_scorer_layout.gone()
        act_detail_lineup_layout.gone()
    }

    override fun checkNetworkConnection() : Boolean? {
        return NetworkHelper.isNetworkAvailable(this)
    }

    private fun onLayoutRefresh(eventId :String?) {
        act_detail_refresh_layout.setOnRefreshListener {
            act_detail_refresh_layout.isRefreshing = false
            mViewModel.startTask(eventId)
            liveDataObserver()
        }
    }

    private fun liveDataObserver() {
        mViewModel.getDataResult().observe(this, Observer {
            bindData(it)
            mMatch = it
        })
    }

    private fun bindData(match: Match?) {
        act_detail_txt_event_id.text = match?.eventId
        item_detail_txt_match_date_time.text = getEventDatetime(match?.eventDate, match?.eventTime)
        item_detail_txt_match_stadium.text = DataHelper.getTeamStadium(this, match?.homeId)
        item_detail_txt_home_name.text = match?.homeName
        item_detail_txt_home_score.text = match?.homeScore
        item_detail_txt_home_formation.text = match?.homeFormation
        getTeamBandage(match?.homeId, match?.awayId)
        item_detail_txt_away_name.text = match?.awayName
        item_detail_txt_away_score.text = match?.awayScore
        item_detail_txt_away_formation.text = match?.awayFormation
        if (GLOBAL_MATCH_VALUE === PREV_MATCH || GLOBAL_MATCH_VALUE === FAVORITE_MATCH) {
            val homeScorer = implodeExplode(match?.homeGoalDetails)
            val awayScorer = implodeExplode(match?.awayGoalDetails)
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
            item_detail_txt_home_goalkeeper.text = implodeExplode(match?.homeLineupGoalkeeper)
            item_detail_txt_home_defense.text = implodeExplode(match?.homeLineupDefense)
            item_detail_txt_home_midfield.text = implodeExplode(match?.homeLineupMidfield)
            item_detail_txt_home_forward.text = implodeExplode(match?.homeLineupForward)
            item_detail_txt_home_substitutes.text = implodeExplode(match?.homeLineupSubstitutes)
            item_detail_txt_away_goalkeeper.text = implodeExplode(match?.awayLineupGoalkeeper)
            item_detail_txt_away_defense.text = implodeExplode(match?.awayLineupDefense)
            item_detail_txt_away_midfield.text = implodeExplode(match?.awayLineupMidfield)
            item_detail_txt_away_forward.text = implodeExplode(match?.awayLineupForward)
            item_detail_txt_away_substitutes.text = implodeExplode(match?.awayLineupSubstitutes)
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
                .into(item_detail_img_home_bandage)
        Picasso.get()
                .load(DataHelper.getTeamBandage(this, awayId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(item_detail_img_away_bandage)
    }

}
