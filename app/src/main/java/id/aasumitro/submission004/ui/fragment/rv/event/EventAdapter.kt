package id.aasumitro.submission004.ui.fragment.rv.event

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.submission004.data.models.Match
import id.aasumitro.submission004.R
import id.aasumitro.submission004.data.sources.EventRepository
import id.aasumitro.submission004.data.sources.remote.ApiClient

class EventAdapter (private val data: ArrayList<Match>,
                    private val listener: EventListener) :
        RecyclerView.Adapter<EventViewHolder>() {

    private val mApiClient = ApiClient()
    private var mRepository: EventRepository? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        mRepository = EventRepository(parent.context, mApiClient)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_match_list, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) =
            holder.bind(data[position], listener, mRepository)

    override fun getItemCount(): Int = data.count()

}