package id.aasumitro.submission004.ui.fragment.rv.favorite

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.submission004.data.models.Favorite
import id.aasumitro.submission004.R

class FavoriteAdapter (private val data: ArrayList<Favorite>,
                       private val listener: FavoriteListener):
        RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_list, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
            holder.bind(data[position], listener)

    override fun getItemCount(): Int = data.count()

}