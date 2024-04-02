package com.sedat.movieappv2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sedat.movieappv2.data.remote.model.Result

@Entity(tableName = "FAVOURITES")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val url: String,
    val title: String,
    val releaseDate: String,
    val imdb: Float,
    val isFavourite: Boolean,
    val createdAt: Long
){
    fun toResult() = Result(
        adult = true,
        backdropPath = "",
        genreIds = listOf(),
        id = movieId,
        originalLanguage = "",
        originalTitle = "",
        overview = "",
        popularity = 1.0f,
        posterPath = url,
        releaseDate = releaseDate,
        title = title,
        video = false,
        voteAverage = imdb,
        voteCount = 0
    )
}