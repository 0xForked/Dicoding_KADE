package id.aasumitro.finalsubmission.ui.fragment.overview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.ui.activity.detail.team.TeamDetailsActivity.Companion.teamDetail
import kotlinx.android.synthetic.main.fragment_team_overview.*
import org.jetbrains.anko.toast

class TeamOverviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_team_overview, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val teams = teamDetail
        initView(teams)
    }

    private fun initView(team: Team?) {
        fr_team_overview_txt.text = team?.description
    }

}