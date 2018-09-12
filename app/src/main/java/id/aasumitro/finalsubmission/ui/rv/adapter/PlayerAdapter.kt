package id.aasumitro.finalsubmission.ui.rv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.finalsubmission.data.model.Player
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.ui.rv.listener.PlayerListener
import id.aasumitro.finalsubmission.ui.rv.holder.PlayerViewHolder

class PlayerAdapter(private val data: ArrayList<Player>,
                    private val listener: PlayerListener) :
        RecyclerView.Adapter<PlayerViewHolder>() {

    override fun getItemCount(): Int = data.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_player_list, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) =
            holder.bind(data[position], listener)

}