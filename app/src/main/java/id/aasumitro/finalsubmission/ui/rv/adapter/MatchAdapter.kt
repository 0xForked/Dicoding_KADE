package id.aasumitro.finalsubmission.ui.rv.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.finalsubmission.data.model.Match
import id.aasumitro.finalsubmission.data.source.Repository
import id.aasumitro.finalsubmission.R
import id.aasumitro.finalsubmission.ui.rv.listener.MatchListener
import id.aasumitro.finalsubmission.ui.rv.holder.MatchViewHolder

class MatchAdapter (private val data: ArrayList<Match>,
                    private val listener: MatchListener,
                    private val adapterStatus: String) :
        RecyclerView.Adapter<MatchViewHolder>() {

    private var mRepository: Repository? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        mRepository = Repository(parent.context)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_list, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) =
            holder.bind(data[position], listener, mRepository, adapterStatus)

    override fun getItemCount(): Int = data.count()

}