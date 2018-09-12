package id.aasumitro.finalsubmission.ui.dialog.player

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.data.model.Player
import id.aasumitro.finalsubmission.util.BitmapTransform
import kotlinx.android.synthetic.main.dialog_player_detail.*
import kotlinx.android.synthetic.main.item_player_list.view.*

class PlayerDetailDialog : Activity() {

    companion object {
        const val INTENT_DATA_PLAYER = "INTENT_PLAYER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_player_detail)
        val mPlayer =
                intent.getParcelableExtra<Player>(INTENT_DATA_PLAYER)
        initView(mPlayer)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(player: Player?) {
        Picasso.get()
                .load(player?.image)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(BitmapTransform.MAX_WIDTH, BitmapTransform.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(BitmapTransform.SIZE, BitmapTransform.SIZE)
                .centerInside()
                .into(dialog_player_cutout)
        dialog_player_name.text = player?.name
        dialog_player_born.text = "${player?.birthLocation}, ${player?.born}"
        dialog_weight.text = player?.weight
        dialog_description.text = player?.description
        if (player?.height?.length as Int > 4) {
            dialog_height.text = player.height?.substring(0,4)
        } else {
            dialog_height.text = player.height
        }
    }

}