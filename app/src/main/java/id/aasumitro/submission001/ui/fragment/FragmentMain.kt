package id.aasumitro.submission001.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.aasumitro.submission001.data.model.ResultData
import id.aasumitro.submission001.ui.fragment.rv.FMListener
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.ctx
import id.aasumitro.submission001.data.remote.ApiClient
import id.aasumitro.submission001.ui.activity.detail.DetailActivity
import id.aasumitro.submission001.util.AppConst.SOURCE_KEY
import id.aasumitro.submission001.util.AppConst.API_KEY
import id.aasumitro.submission001.ui.fragment.rv.FMAdapter
import org.jetbrains.anko.support.v4.startActivity

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class FragmentMain : Fragment(), AnkoLogger, FMNavigator, FMListener {

    private var mApiClient = ApiClient()
    private var fmUI = FragmentMainUI()

    private val mFMViewModel: FMVIewModel by lazy {
        ViewModelProviders.of(this).get(FMVIewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fmUI = FragmentMainUI()
        return fmUI .createView(AnkoContext.create(ctx, this))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState.let {
            mFMViewModel.initViewModel(this, mApiClient)
            mFMViewModel.startTask(SOURCE_KEY, API_KEY)
        }

        initRecyclerView()
        onLayoutRefreshed()

    }

    private fun onLayoutRefreshed() {
        fmUI.mSwipeRefresh.setOnRefreshListener {
            fmUI.mSwipeRefresh.isRefreshing = false
            mFMViewModel.startTask(SOURCE_KEY, API_KEY)
        }
    }

    private fun initRecyclerView() {
        fmUI.mRecyclerVIew.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager =
                LinearLayoutManager(activity)
        fmUI.mRecyclerVIew.layoutManager = layoutManager
        fmUI.mRecyclerVIew.itemAnimator = DefaultItemAnimator()
    }

    override fun initAdapter() {
        fmUI.mRecyclerVIew.adapter =
                FMAdapter(mFMViewModel.newsList, this)
    }

    override fun onItemPressed(result: ResultData) {
        val author: String = result.author ?: SOURCE_KEY
        startActivity<DetailActivity>(
                "author" to author,
                "title" to result.title,
                "desc" to result.description,
                "url" to result.url,
                "urlToImage" to result.urlToImage,
                "publishedAt" to result.publishedAt)

    }

    override fun onPause() {
        super.onPause()
        fmUI.mRecyclerVIew
                .recycledViewPool
                .clear()
    }


}