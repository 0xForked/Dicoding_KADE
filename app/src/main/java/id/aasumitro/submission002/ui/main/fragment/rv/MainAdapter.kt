package id.aasumitro.submission002.ui.main.fragment.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.submission002.R
import id.aasumitro.submission002.data.model.Match

class MainAdapter (private val dataList: ArrayList<Match>,
                   private val listener: MainListener) :
        RecyclerView.Adapter<MainViewHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
            holder.bind(dataList[position], listener, mContext)

    override fun getItemCount(): Int = dataList.count()

}