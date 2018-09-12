package id.aasumitro.finalsubmission.ui.rv.holder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import kotlinx.android.synthetic.main.item_match_list.view.*
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.util.*
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_HEIGHT
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.MAX_WIDTH
import id.aasumitro.finalsubmission.util.BitmapTransform.Companion.SIZE
import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatDate
import id.aasumitro.finalsubmission.util.DateTimeHelper.reformatTime
import id.aasumitro.finalsubmission.util.GlobalConst.MATCH_STATUS_FT
import id.aasumitro.finalsubmission.util.GlobalConst.MATCH_STATUS_VS
import id.aasumitro.finalsubmission.util.GlobalConst.NEXT_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.PAST_MATCH
import id.aasumitro.finalsubmission.util.GlobalConst.SEARCH_MATCH

class MatchViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

    fun bind(match: Match?, listener: MatchListener?,
             repository: Repository?, adapterStatus: String?) {

        itemView.item_rv_txt_home_score.text = match?.homeScore
        itemView.item_rv_txt_away_score.text = match?.awayScore

        itemView.item_rv_match_date.text = reformatDate(match?.date)

        if (match?.time != null) {
            itemView.item_rv_match_time.text = reformatTime(match.time)
        }

        if (adapterStatus == NEXT_MATCH || adapterStatus == PAST_MATCH) {

            Picasso.get()
                    .load(repository?.getTeamBandage(match?.homeId))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_broken_image)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(SIZE, SIZE)
                    .centerInside()
                    .into(itemView.item_rv_team_home_bandage)

            Picasso.get()
                    .load(repository?.getTeamBandage(match?.awayId))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_broken_image)
                    .transform(BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .resize(SIZE, SIZE)
                    .centerInside()
                    .into(itemView.item_rv_team_away_bandage)

            if (adapterStatus == NEXT_MATCH) {
                itemView.item_rv_txt_match_status.text = MATCH_STATUS_VS
                itemView.alarm_icon.visible()
            }

            if (adapterStatus == PAST_MATCH) {
                itemView.item_rv_txt_match_status.text = MATCH_STATUS_FT
            }

        }

        if (adapterStatus == SEARCH_MATCH) {
            itemView.item_rv_team_away_bandage.gone()
            itemView.item_rv_team_home_bandage.gone()
            itemView.item_rv_team_away_name.visible()
            itemView.item_rv_team_home_name.visible()

            itemView.item_rv_team_home_name.text = match?.homeName
            itemView.item_rv_team_away_name.text = match?.awayName

        }

        itemView.item_rv_match_lay_click.setOnClickListener { listener?.onMatchPressed(match) }

    }

}