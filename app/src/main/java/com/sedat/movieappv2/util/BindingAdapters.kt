package com.sedat.movieappv2.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    load(url)
}

fun View.hide(){
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}

fun View.show(){
    if(this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}