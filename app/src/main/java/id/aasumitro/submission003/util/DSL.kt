package id.aasumitro.submission003.util

import android.content.Context
import android.view.View
import id.aasumitro.submission003.data.source.local.anko.AnkoHelper

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

val Context.ankoDB: AnkoHelper
    get() = AnkoHelper.getInstance(applicationContext)