package id.aasumitro.finalsubmission.ui.rv.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.finalsubmission.data.model.Player
import kotlinx.android.synthetic.main.item_player_list.view.*
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.ui.rv.listener.PlayerListener
import id.aasumitro.finalsubmission.util.BitmapTransform

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(player: Player, listener: PlayerListener) {

        itemView.item_rv_player_name.text = player.name
        itemView.item_rv_player_lineup.text = player.position
        Picasso.get()
                .load(player.image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(BitmapTransform.MAX_WIDTH, BitmapTransform.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(BitmapTransform.SIZE, BitmapTransform.SIZE)
                .centerInside()
                .into(itemView.item_rv_player_cutout)

        itemView.item_rv_player_lay_click.setOnClickListener { listener.onPlayerPressed(player) }

    }

}