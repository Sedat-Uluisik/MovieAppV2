package com.sedat.movieappv2.presentation.moviefavourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.data.local.entity.MovieEntity
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.databinding.MovieListItemBinding

class MovieFavouritesAdapter: PagingDataAdapter<MovieEntity, MovieFavouritesAdapter.FavouritesHolder>(MovieFavouritesDiffUtil()) {

    class MovieFavouritesDiffUtil: DiffUtil.ItemCallback<MovieEntity>(){
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((Int) -> Unit) ?= null
    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: FavouritesHolder, position: Int) {
        val movieEntity = getItem(position)!!

        holder.bind(movieEntity.toResult())

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(movieEntity.movieId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouritesHolder(binding)
    }

    class FavouritesHolder(private val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.apply {
                movie = result
                executePendingBindings()
            }
        }
    }
}