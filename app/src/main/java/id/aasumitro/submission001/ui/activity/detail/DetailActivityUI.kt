package id.aasumitro.submission001.ui.activity.detail

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import id.aasumitro.submission001.R

class DetailActivityUI : AnkoComponent<DetailActivity> {

    lateinit var mTitle: TextView
    lateinit var mSubtitle: TextView
    lateinit var mDescription: TextView
    lateinit var mFeaturedImage: ImageView

    override fun createView(ui: AnkoContext<DetailActivity>): View = with(ui) {
        constraintLayout {
            relativeLayout {
                id = R.id.detail_main_layout

                // TITLE
                mTitle = textView {
                    id = R.id.detail_text_title
                    textSize = 25f
                    typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                    hint = "TEXT_TITLE"
                    gravity = Gravity.CENTER
                }.lparams(
                        width = wrapContent,
                        height = wrapContent
                ) {
                    topMargin = dip(5)
                    marginStart = dip(5)
                    endOf(R.id.card_item_image)
                    centerHorizontally()
                }

                // SUBTITLE
                mSubtitle = textView {
                    id = R.id.detail_text_subtitle
                    textSize = 15f
                    hint = "TEXT_SUBTITLE"

                }.lparams(
                        width = wrapContent,
                        height = wrapContent
                ) {
                    topMargin = dip(5)
                    below(R.id.detail_text_title)
                    centerHorizontally()
                }

                // FEATURED IMAGE
                mFeaturedImage = imageView {
                    id = R.id.detail_image_view
                    contentDescription = "@Null"
                }.lparams(
                        width = matchParent,
                        height = wrapContent
                ) {
                    below(R.id.detail_text_subtitle)
                    topMargin = dip(10)
                }

                // DESCRIPTION
                mDescription = textView {
                    id = R.id.detail_text_description
                    hint = "TEXT_DESCRIPTION"
                    textSize = 18f
                }.lparams(
                        width = wrapContent,
                        height = wrapContent
                ) {
                    below(R.id.detail_image_view)
                    topMargin = dip(10)
                }

            }.lparams(
                    width = matchParent,
                    height = matchParent
            ) {
                padding = dip(20)
            }

        }

    }

}