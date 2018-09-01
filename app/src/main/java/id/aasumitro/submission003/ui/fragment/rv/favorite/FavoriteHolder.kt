package id.aasumitro.submission003.ui.fragment.rv.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission003.data.model.Favorite
import id.aasumitro.submission003.util.*
import kotlinx.android.synthetic.main.item_match_list.view.*
import id.aasumitro.submission003.R

class FavoriteHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bind(favorite: Favorite?, listener: FavoriteListener) {

        itemView.item_rv_txt_home_score.text = favorite?.homeScore
        itemView.item_rv_txt_away_score.text = favorite?.awayScore

        Picasso.get()
                .load(favorite?.homeBandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(itemView.item_rv_team_home_bandage)

        Picasso.get()
                .load(favorite?.awayBandage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(itemView.item_rv_team_away_bandage)

        itemView.item_rv_match_date.text = DateTime().reformatDate(favorite?.eventDate)
        itemView.item_rv_match_time.text = DateTime().reformatTime(favorite?.eventTime)
        itemView.item_rv_txt_match_status.text = AppConst.MATCH_STATUS_FT

        if (favorite?.awayScore === null && favorite?.homeScore === null ||
                favorite.awayScore === "" && favorite.homeScore === "") {
            itemView.item_rv_txt_match_status.text = AppConst.MATCH_STATUS_VS
        }

        itemView.item_rv_lay_click.setOnClickListener { listener.onFavoritePressed(favorite) }

    }

}