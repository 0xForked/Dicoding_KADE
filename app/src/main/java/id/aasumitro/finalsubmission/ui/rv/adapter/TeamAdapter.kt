package id.aasumitro.finalsubmission.ui.rv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.ui.rv.listener.TeamListener
import id.aasumitro.finalsubmission.ui.rv.holder.TeamViewHolder

class TeamAdapter(private val data: ArrayList<Team>,
                  private val listener: TeamListener):
        RecyclerView.Adapter<TeamViewHolder>() {

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_team_list, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) =
            holder.bind(data[position], listener)

}