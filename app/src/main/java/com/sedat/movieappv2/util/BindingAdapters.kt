package com.sedat.movieappv2.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import coil.size.Precision
import coil.size.Scale
import com.sedat.movieappv2.R

@BindingAdapter("loadImage")
fun ImageView.loadImage(url: String?) {
    load(url)
}

@BindingAdapter("isFavorite")
fun ImageView.isFavorite(isFavorite: Boolean = false){
    if(isFavorite)
        this.setImageResource(R.drawable.ic_favorite_32)
    else
        this.setImageResource(R.drawable.ic_favorite_border_32)
}

fun View.hide(){
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}

fun View.show(){
    if(this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}