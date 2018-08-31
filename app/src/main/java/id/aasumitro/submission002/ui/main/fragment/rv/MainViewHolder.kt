package id.aasumitro.submission002.ui.main.fragment.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission002.R
import id.aasumitro.submission002.data.model.Match
import id.aasumitro.submission002.util.*
import id.aasumitro.submission002.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission002.util.AppConst.MATCH_STATUS_FT
import id.aasumitro.submission002.util.AppConst.MATCH_STATUS_VS
import id.aasumitro.submission002.util.AppConst.MAX_HEIGHT
import id.aasumitro.submission002.util.AppConst.MAX_WIDTH
import id.aasumitro.submission002.util.AppConst.NEXT_MATCH
import id.aasumitro.submission002.util.AppConst.PREV_MATCH
import id.aasumitro.submission002.util.DataHelper.getTeamBandage
import kotlinx.android.synthetic.main.item_match_list.view.*

class MainViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bind(match: Match, listener: MainListener, context: Context?) {

        itemView.team_home_name.text = match.homeName
        itemView.team_home_score.text = match.homeScore

        itemView.team_away_name.text = match.awayName
        itemView.team_away_score.text = match.awayScore

        itemView.match_date.text = DateTime().reformatDate(match.eventDate)
        itemView.match_time.text = DateTime().reformatTime(match.eventTime)

        when(GLOBAL_MATCH_VALUE) {
            PREV_MATCH -> {
                itemView.match_status.text = MATCH_STATUS_FT
                itemView.team_home_score.visible()
                itemView.team_away_score.visible()
            }
            NEXT_MATCH -> {
                itemView.match_status.text = MATCH_STATUS_VS
                itemView.team_home_bandage.visible()
                itemView.team_away_bandage.visible()
                itemView.team_away_name.gone()
                itemView.team_home_name.gone()

                Picasso.get()
                        .load(getTeamBandage(context, match.homeId))
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_broken_image)
                        .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .resize(AppConst.SIZE, AppConst.SIZE)
                        .centerInside()
                        .into(itemView.team_home_bandage)

                Picasso.get()
                        .load(getTeamBandage(context, match.awayId))
                        .placeholder(R.drawable.ic_image)
                        .error(R.drawable.ic_broken_image)
                        .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .resize(AppConst.SIZE, AppConst.SIZE)
                        .centerInside()
                        .into(itemView.team_away_bandage)

            }
        }

        itemView.lay_click.setOnClickListener { listener.onMatchPressed(match) }

    }

}