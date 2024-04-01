package com.sedat.movieappv2.interfaces

import android.view.View
import com.sedat.movieappv2.data.remote.model.Result

interface MovieItemClickListener {
    fun onItemClick(view: View, movieId: Int)

}

interface FavouriteBtnClickListener{
    fun onFavouriteBtnClick(view: View, movie: Result)
}