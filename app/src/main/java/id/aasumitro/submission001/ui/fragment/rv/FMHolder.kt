package id.aasumitro.submission001.ui.fragment.rv

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission001.data.model.ResultData
import id.aasumitro.submission001.R
import id.aasumitro.submission001.util.AppConst
import id.aasumitro.submission001.util.BitmapTransform
import id.aasumitro.submission001.util.dateHelper
import kotlinx.android.synthetic.main.item_list.view.*

class FMHolder (view: View) : RecyclerView.ViewHolder(view) {

    fun bind(result: ResultData, listener: FMListener, context: Context?) {
        Picasso.get()
                .load(result.urlToImage)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(itemView.img_item)
        itemView.txt_item_title.text = result.title
        itemView.txt_item_subtitle.text = context?.getString(R.string.bbc_news)
        itemView.txt_item_published.text = dateHelper(result.publishedAt)
        itemView.lay_vertical_click.setOnClickListener { listener.onItemPressed(result) }
    }
}