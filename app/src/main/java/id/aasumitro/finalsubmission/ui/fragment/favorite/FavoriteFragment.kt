package id.aasumitro.finalsubmission.ui.fragment.favorite

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity.Companion.INTENT_DATA_EVENT_ID
import id.aasumitro.finalsubmission.ui.activity.detail.match.MatchDetailActivity.Companion.INTENT_MATCH_TYPE
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity.Companion.INTENT_TEAM_EXTRA
import id.aasumitro.finalsubmission.ui.rv.listener.TeamListener
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_CODE_EMPTY_VALUE
import id.aasumitro.finalsubmission.util.GlobalConst.ERROR_MESSAGE_EMPTY_FAVORITE_LIST
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import id.aasumitro.finalsubmission.ui.rv.adapter.TeamAdapter
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.ui.rv.adapter.MatchAdapter
import id.aasumitro.finalsubmission.util.GlobalConst.PAST_MATCH


class FavoriteFragment : Fragment(), FavoriteNavigator, TeamListener, MatchListener {

    private var mRepository: Repository? = null

    private val mViewModel: FavoriteViewModel by lazy {
        ViewModelProviders
                .of(activity as FragmentActivity)
                .get(FavoriteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mRepository = Repository(activity)
        mViewModel.initVM(this, mRepository as Repository)
        mViewModel.getFavoriteTeam()
        mViewModel.getFavoriteMatch()
        initRecyclerView()
        initRefreshLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.menu_search).isVisible = false
    }

    override fun makeToast(message: String): Toast? = activity?.toast(message)

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_favorite_main_layout.visible()
            fr_favorite_error_layout.gone()
        } else {
            fr_favorite_main_layout.gone()
            fr_favorite_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_favorite_loading_layout.visible()
            fr_favorite_error_layout.gone()
            fr_favorite_main_layout.gone()
        } else {
            fr_favorite_loading_layout.gone()
            fr_favorite_main_layout.visible()
        }
    }

    private fun initRefreshLayout() {
        fr_favorite_refresh_layout.setOnRefreshListener {
            fr_favorite_refresh_layout.isRefreshing = false
            mViewModel.getFavoriteTeam()
            mViewModel.getFavoriteMatch()
            validateData()
        }
    }

    private fun initRecyclerView() {
        val layoutManagerTeam : RecyclerView.LayoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val layoutManagerMatch : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)

        rv_favorite_match.setHasFixedSize(true)
        rv_favorite_match.layoutManager = layoutManagerMatch
        rv_favorite_match.itemAnimator = DefaultItemAnimator()

        rv_favorite_team.setHasFixedSize(true)
        rv_favorite_team.layoutManager = layoutManagerTeam
        rv_favorite_team.itemAnimator = DefaultItemAnimator()
    }

    override fun initTeamListAdapter() {
        rv_favorite_team.adapter =
                TeamAdapter(mViewModel.mTeamData,
                        this)
    }

    override fun initMatchListAdapter() {
        rv_favorite_match.adapter =
                MatchAdapter(mViewModel.mMatchData,
                        this,
                        PAST_MATCH)
    }

    private fun validateData() {
        val favoriteTeam = mViewModel.mTeamData
        val favoriteMatch = mViewModel.mMatchData
        if (favoriteMatch.size == 0 && favoriteTeam.size == 0) {
            onSuccess(false,
                    ERROR_CODE_EMPTY_VALUE,
                    ERROR_MESSAGE_EMPTY_FAVORITE_LIST)
        }
    }

    override fun clearRecyclerView() {
        rv_favorite_team.recycledViewPool.clear()
        rv_favorite_match.recycledViewPool.clear()
    }

    override fun onTeamPressed(team: Team?) {
        activity?.startActivity<TeamDetailsActivity>(
                INTENT_TEAM_EXTRA to team)
    }

    override fun onMatchPressed(match: Match?) {
        activity?.startActivity<MatchDetailActivity>(
                INTENT_DATA_EVENT_ID to match?.uniqueId,
                INTENT_MATCH_TYPE to match?.favoriteType)
    }

}