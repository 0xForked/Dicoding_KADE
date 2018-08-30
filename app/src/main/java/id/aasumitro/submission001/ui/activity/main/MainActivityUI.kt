package id.aasumitro.submission001.ui.activity.main

import android.view.View
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import id.aasumitro.submission001.R

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class MainActivityUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>): View  = with(ui) {
        constraintLayout {
            frameLayout {
                id = R.id.main_frame
            }.lparams(width = matchParent, height = matchParent)
        }
    }

}