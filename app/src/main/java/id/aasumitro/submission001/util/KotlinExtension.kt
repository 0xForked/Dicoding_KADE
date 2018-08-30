package id.aasumitro.submission001.util

import android.util.TypedValue
import android.view.View
import org.jetbrains.anko.backgroundResource
import id.aasumitro.submission001.R

/**
 * Created by Agus Adhi Sumitro on 28/08/2018.
 * https://aasumitro.id
 * aasumitro@gmail.com
 */

fun View.onClickBackground() {
    val outValue = TypedValue()
    context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
    this.backgroundResource = outValue.resourceId
}
