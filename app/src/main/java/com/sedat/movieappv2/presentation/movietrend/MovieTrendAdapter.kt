package com.sedat.movieappv2.presentation.movietrend

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.databinding.MovieListItemBinding
import com.sedat.movieappv2.interfaces.MovieItemClickListener

class MovieTrendAdapter : PagingDataAdapter<Result, MovieTrendAdapter.Holder>(MovieDiffUtil()), MovieItemClickListener {

    class MovieDiffUtil: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val result = getItem(position)!!

        holder.bind(result)
        holder.binding.itemClick = this

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrendAdapter.Holder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.apply {
                movie = result
                executePendingBindings()
            }
        }
    }

    override fun onItemClick(view: View, movieId: Int) {
        val action = MovieTrendFragmentDirections.actionMovieTrendFragmentToMovieDetailsFragment(movieid = movieId)
        view.findNavController().navigate(action)
    }
}