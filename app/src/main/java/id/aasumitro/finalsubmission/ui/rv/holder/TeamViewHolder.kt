package id.aasumitro.finalsubmission.ui.rv.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Team
import id.aasumitro.finalsubmission.ui.rv.listener.TeamListener
import id.aasumitro.finalsubmission.util.BitmapTransform
import kotlinx.android.synthetic.main.item_team_list.view.*

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(team: Team, listener: TeamListener) {

        Picasso.get()
                .load(team.bandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(BitmapTransform.MAX_WIDTH, BitmapTransform.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(BitmapTransform.SIZE, BitmapTransform.SIZE)
                .centerInside()
                .into(itemView.item_rv_team_bandage)

        itemView.item_rv_team_name.text = team.name

        itemView.item_rv_team_lay_click.setOnClickListener { listener.onTeamPressed(team) }

    }

}