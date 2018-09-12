package id.aasumitro.finalsubmission.ui.activity.detail.team

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_details.*
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.util.BitmapTransform
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_HEIGHT
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_WIDTH
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.SIZE
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import id.aasumitro.finalsubmission.data.source.Repository
import org.jetbrains.anko.indeterminateProgressDialog


class TeamDetailsActivity : AppCompatActivity(), TeamDetailNavigator {

    companion object {
        const val INTENT_TEAM_EXTRA = "INTENT_EXTRA"
        var teamDetail: Team? = null
    }

    private var mRepository: Repository? = null
    private var mMenuItem: Menu? = null
    private var isFavoriteMatch: Boolean = false
    private var mTeamId: String? = null

    private val mViewModel: TeamDetailViewModel by lazy {
        ViewModelProviders
                .of(this)
                .get(TeamDetailViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        setSupportActionBar(toolbar)
        mRepository = Repository(this)
        mViewModel.initVM(this, mRepository as Repository)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val team = intent.getParcelableExtra<Team>(INTENT_TEAM_EXTRA)
        teamDetail = team as Team
        mTeamId = team.uniqueId
        val fragmentAdapter =
                TeamViewPagerAdapter(supportFragmentManager as FragmentManager)
        act_team_detail_view_pager.adapter = fragmentAdapter
        act_team_detail_tab_layout.setupWithViewPager(act_team_detail_view_pager)
        initView(team)
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
                    mViewModel.removeFromFavorite(mTeamId)
                } else {
                    mViewModel.favoriteTask(mTeamId)
                }
                isFavoriteMatch = !isFavoriteMatch
                favoriteStatus()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favoriteState() {
        val favoriteState =
                mViewModel.favoriteState(mTeamId as String)
        isFavoriteMatch = favoriteState
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

    private fun initView(team: Team) {
        app_bar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = true
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_layout.title = team.name
                    isShow = true
                } else if (isShow) {
                    toolbar_layout.title = " "
                    isShow = false
                }
            }
        })

        Picasso.get()
                .load(team.bandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(SIZE, SIZE)
                .centerInside()
                .into(act_team_detail_bandage)
        act_team_detail_name.text = team.name
        act_team_detail_formed_year.text = team.formedYear
        act_team_detail_stadium.text = team.stadium

    }

    override fun progressDialog(isLoading: Boolean) {
        val dialog =
                indeterminateProgressDialog(message = "Please wait a bit…", title = "Sync data")
        dialog.setCancelable(false)
        if (isLoading) {
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }
}
