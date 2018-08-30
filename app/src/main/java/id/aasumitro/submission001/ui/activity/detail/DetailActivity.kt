package id.aasumitro.submission001.ui.activity.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import id.aasumitro.submission001.R
import id.aasumitro.submission001.util.AppConst
import id.aasumitro.submission001.util.BitmapTransform
import id.aasumitro.submission001.util.dateHelper
import org.jetbrains.anko.setContentView

class DetailActivity : AppCompatActivity() {

    private var maUI = DetailActivityUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        maUI = DetailActivityUI()
        maUI.setContentView(this)
        dataSetter()
    }

    private fun dataSetter() {
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("desc")
        val urlToImage = intent.getStringExtra("urlToImage")
        val published = intent.getStringExtra("publishedAt")
        val subtitle = getString(R.string.bbc_news) + " - " + dateHelper(published)
        maUI.mTitle.text = title
        maUI.mSubtitle.text = subtitle
        maUI.mDescription.text = description
        getFeaturedImage(urlToImage)
    }

    private fun getFeaturedImage(imgUrl: String?) {
        Picasso.get()
                .load(imgUrl)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_broken_image)
                .transform(BitmapTransform(AppConst.MAX_WIDTH, AppConst.MAX_HEIGHT))
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(AppConst.SIZE, AppConst.SIZE)
                .centerInside()
                .into(maUI.mFeaturedImage)
    }

}