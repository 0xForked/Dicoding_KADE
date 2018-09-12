package id.aasumitro.finalsubmission.ui.fragment.team

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.data.source.prefs.SharedPrefs.KEY_LEAGUE_NAME
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity.Companion.INTENT_TEAM_EXTRA
import id.aasumitro.finalsubmission.ui.rv.listener.TeamListener
import id.aasumitro.finalsubmission.util.NetworkHelper.isNetworkAvailable
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import id.aasumitro.finalsubmission.ui.rv.adapter.TeamAdapter

class TeamFragment : Fragment(), TeamNavigator, TeamListener {

    private var mRepository: Repository? = null
    private var mLeagueName: String? = null

    private val mViewModel: TeamViewModel by lazy {
        ViewModelProviders
                .of(activity as FragmentActivity)
                .get(TeamViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_team, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mRepository = Repository(activity)
        mLeagueName = mRepository?.getPrefs(KEY_LEAGUE_NAME)
        mViewModel.initVM(this, mRepository as Repository)
        mViewModel.startTask(mLeagueName as String)
        initRefreshLayout()
        initTeamList()
    }

    override fun toast(message: String): Toast? =
            activity?.toast(message)

    override fun checkNetworkConnection(): Boolean =
            isNetworkAvailable(activity as FragmentActivity)

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_team_refresh_layout.visible()
            fr_team_error_layout.gone()
        } else {
            fr_team_refresh_layout.gone()
            fr_team_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_team_loading_layout.visible()
            fr_team_refresh_layout.gone()
            fr_team_error_layout.gone()
        } else {
            fr_team_loading_layout.gone()
            fr_team_refresh_layout.visible()
        }
    }

    private fun initRefreshLayout() {
        fr_team_refresh_layout.setOnRefreshListener {
            fr_team_refresh_layout.isRefreshing = false
            mViewModel.startTask(mLeagueName as String)
        }
    }

    private fun initTeamList() {
        fr_team_rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                GridLayoutManager(activity, 3)
        fr_team_rv.layoutManager = layoutManager
        fr_team_rv.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        fr_team_rv.adapter =
                TeamAdapter(mViewModel.mTeamData,
                        this)
    }

    override fun clearRecyclerView() {
        fr_team_rv.recycledViewPool.clear()
    }

    override fun onTeamPressed(team: Team?) {
        activity?.startActivity<TeamDetailsActivity>(
                INTENT_TEAM_EXTRA to team)
    }

}