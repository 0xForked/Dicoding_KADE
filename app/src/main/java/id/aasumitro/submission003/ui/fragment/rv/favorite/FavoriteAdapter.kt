package id.aasumitro.submission003.ui.fragment.rv.favorite

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.submission003.R
import id.aasumitro.submission003.data.model.Favorite

class FavoriteAdapter (private val dataList: ArrayList<Favorite>,
                       private val listener: FavoriteListener):
        RecyclerView.Adapter<FavoriteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_list, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) =
            holder.bind(dataList[position], listener)

    override fun getItemCount(): Int = dataList.count()

}