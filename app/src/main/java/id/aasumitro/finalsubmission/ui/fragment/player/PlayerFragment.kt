package id.aasumitro.finalsubmission.ui.fragment.player

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Player
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity.Companion.teamDetail
import id.aasumitro.finalsubmission.ui.dialog.player.PlayerDetailDialog
import id.aasumitro.finalsubmission.ui.dialog.player.PlayerDetailDialog.Companion.INTENT_DATA_PLAYER
import id.aasumitro.finalsubmission.ui.rv.listener.PlayerListener
import id.aasumitro.finalsubmission.util.NetworkHelper.isNetworkAvailable
import id.aasumitro.finalsubmission.util.gone
import id.aasumitro.finalsubmission.util.visible
import kotlinx.android.synthetic.main.fragment_player.*
import kotlinx.android.synthetic.main.item_error_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import id.aasumitro.finalsubmission.ui.rv.adapter.PlayerAdapter

class PlayerFragment : Fragment(), PlayerListener, PlayerNavigator {

    private var mRepository: Repository? = null
    private var mTeamData: Team? = null

    private val mViewModel: PlayerViewModel by lazy {
        ViewModelProviders
                .of(activity as FragmentActivity)
                .get(PlayerViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_player, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mTeamData = teamDetail
        mRepository = Repository(activity)
        mViewModel.initVM(this, mRepository as Repository)
        mViewModel.startTask(mTeamData?.uniqueId)
        initRefreshLayout()
        initTeamList()
    }

    override fun showToast(message: String): Toast? =
            activity?.toast(message)

    override fun checkNetworkConnection(): Boolean =
            isNetworkAvailable(activity as FragmentActivity)

    override fun onSuccess(isSuccess: Boolean, code: String?, message: String?) {
        if (isSuccess) {
            fr_player_refresh_layout.visible()
            fr_player_error_layout.gone()
        } else {
            fr_player_refresh_layout.gone()
            fr_player_error_layout.visible()
            item_error_txt_code.text = code
            item_error_txt_message.text = message
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) {
            fr_player_loading_layout.visible()
            fr_player_refresh_layout.gone()
            fr_player_error_layout.gone()
        } else {
            fr_player_loading_layout.gone()
            fr_player_refresh_layout.visible()
        }
    }

    private fun initRefreshLayout() {
        fr_player_refresh_layout.setOnRefreshListener {
            fr_player_refresh_layout.isRefreshing = false
            mViewModel.startTask(mTeamData?.uniqueId)
        }
    }

    private fun initTeamList() {
        fr_player_rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        fr_player_rv.layoutManager = layoutManager
        fr_player_rv.itemAnimator = DefaultItemAnimator()
    }

    override fun initListAdapter() {
        fr_player_rv.adapter =
                PlayerAdapter(mViewModel.mPlayerData,
                        this)
    }

    override fun clearRecyclerView() {
        fr_player_rv.recycledViewPool.clear()
    }

    override fun onPlayerPressed(player: Player?) {
        activity?.startActivity<PlayerDetailDialog>(
                INTENT_DATA_PLAYER to player)
    }

}