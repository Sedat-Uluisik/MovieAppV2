package com.sedat.movieappv2.data.remote.model.imagemodel

data class MovieImages(
    val backdrops: List<Backdrop>,
    val id: Int,
    val logos: List<Logo>,
    val posters: List<Poster>
)