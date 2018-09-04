package id.aasumitro.submission004.ui.fragment.rv.event

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.data.sources.EventRepository

import com.squareup.picasso.MemoryPolicy
import id.aasumitro.submission004.R
import id.aasumitro.submission004.util.*
import id.aasumitro.submission004.util.AppConst.GLOBAL_MATCH_VALUE
import id.aasumitro.submission004.util.AppConst.MATCH_STATUS_FT
import id.aasumitro.submission004.util.AppConst.MATCH_STATUS_VS
import id.aasumitro.submission004.util.AppConst.MAX_HEIGHT
import id.aasumitro.submission004.util.AppConst.MAX_WIDTH
import id.aasumitro.submission004.util.AppConst.NEXT_MATCH
import id.aasumitro.submission004.util.AppConst.PREV_MATCH
import id.aasumitro.submission004.util.DateTime.reformatDate
import id.aasumitro.submission004.util.DateTime.reformatTime
import kotlinx.android.synthetic.main.item_match_list.view.*

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(match: Match?, listener: EventListener?, repository: EventRepository?) {

        itemView.item_rv_txt_home_score.text = match?.homeScore
        itemView.item_rv_txt_away_score.text = match?.awayScore

        Picasso.get()
                .load(repository?.getTeamBandage(match?.homeId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(itemView.item_rv_team_home_bandage)

        Picasso.get()
                .load(repository?.getTeamBandage(match?.awayId))
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(itemView.item_rv_team_away_bandage)

        itemView.item_rv_match_date.text = reformatDate(match?.eventDate)
        itemView.item_rv_match_time.text = reformatTime(match?.eventTime)

        when(GLOBAL_MATCH_VALUE) {
            PREV_MATCH -> {
                itemView.item_rv_txt_match_status.text = MATCH_STATUS_FT
                // itemView.item_rv_txt_match_status.textColors =
                itemView.item_rv_txt_home_score.visible()
                itemView.item_rv_txt_away_score.visible()
            }
            NEXT_MATCH -> {
                itemView.item_rv_txt_match_status.text = MATCH_STATUS_VS
                itemView.item_rv_team_home_bandage.visible()
                itemView.item_rv_team_away_bandage.visible()
            }
        }

        itemView.item_rv_lay_click.setOnClickListener { listener?.onEventPressed(match) }

    }

}