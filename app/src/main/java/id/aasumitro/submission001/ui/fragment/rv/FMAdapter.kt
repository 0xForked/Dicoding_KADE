package id.aasumitro.submission001.ui.fragment.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import id.aasumitro.submission001.data.model.ResultData
import id.aasumitro.submission001.R

class FMAdapter (private val dataList: ArrayList<ResultData>,
                 private val listener: FMListener) :
        RecyclerView.Adapter<FMHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FMHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
        mContext = parent.context
        return FMHolder(view)

        // Layout yang dari anko
        // return FMHolder(FMItemCardUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FMHolder, position: Int) =
            holder.bind(dataList[position], listener, mContext)

    override fun getItemCount(): Int = dataList.count()

}