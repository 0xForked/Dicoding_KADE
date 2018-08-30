package id.aasumitro.submission001.ui.fragment

import android.graphics.Typeface
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.jetbrains.anko.cardview.v7.cardView
import id.aasumitro.submission001.R
import org.jetbrains.anko.*
import id.aasumitro.submission001.util.onClickBackground


/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

class FMItemCardUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            id = R.id.card_item
            layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                margin = dip(3)
            }

            // Relative layout with onClickAction
            relativeLayout {
                id = R.id.card_item_click
                isClickable = true
                onClickBackground()

                // featured image
                imageView {
                    id = R.id.card_item_image
                    setImageResource(R.mipmap.ic_launcher)
                    contentDescription = "@null"
                }.lparams(width = dip(200), height = matchParent)

                // featured title
                textView {
                    id = R.id.card_item_text_title
                    textSize = 20f
                    typeface = Typeface.create("sans-serif-condensed", Typeface.BOLD)
                    //textColorResource = ContextCompat.getColor(context, R.color.colorAccent)
                    hint = "TEXT_TITLE"
                }.lparams(
                        width = wrapContent,
                        height = wrapContent
                ) {
                    topMargin = dip(5)
                    marginStart = dip(5)
                    endOf(R.id.card_item_image)
                }

                // featured author and date published
                relativeLayout {

                    // author item
                    textView {
                        id = R.id.card_item_text_author
                        textSize = 12f
                        maxLines = 1
                        ellipsize = TextUtils.TruncateAt.END
                        hint = "TEXT_AUTHOR"

                    }.lparams(
                            width = wrapContent,
                            height = wrapContent
                    ) {
                        marginStart = dip(3)
                    }

                    // published item
                    textView {
                        id = R.id.card_item_text_published
                        hint = "TEXT_PUBLISHED"
                    }.lparams(
                            width = wrapContent,
                            height = wrapContent
                    ) {
                        below(R.id.card_item_text_author)
                    }

                }.lparams(
                        width = wrapContent,
                        height = wrapContent
                ) {
                    alignParentBottom()
                    endOf(R.id.card_item_image)
                    marginStart = dip(10)
                    bottomMargin = dip(10)
                }

            }.lparams(width = matchParent, height = dip(130))
        }
    }

}