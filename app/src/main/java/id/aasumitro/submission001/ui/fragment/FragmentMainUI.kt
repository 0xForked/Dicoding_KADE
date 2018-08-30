package id.aasumitro.submission001.ui.fragment

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import org.jetbrains.anko.constraint.layout.constraintLayout
import id.aasumitro.submission001.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class FragmentMainUI : AnkoComponent<FragmentMain> {

    lateinit var mSwipeRefresh: SwipeRefreshLayout
    lateinit var mRecyclerVIew: RecyclerView

    override fun createView(ui: AnkoContext<FragmentMain>): View = with(ui) {
        constraintLayout {
            mSwipeRefresh = swipeRefreshLayout {
                id = R.id.main_swipe_refresh

                mRecyclerVIew = recyclerView {
                    id = R.id.main_recycler_view
                }
            }.lparams(width = matchParent, height = matchParent)
        }
    }

}