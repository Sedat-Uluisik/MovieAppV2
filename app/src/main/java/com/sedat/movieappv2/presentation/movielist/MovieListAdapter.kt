package com.sedat.movieappv2.presentation.movielist

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

class MovieListAdapter : PagingDataAdapter<Result, MovieListAdapter.Holder>(MovieDiffUtil()), MovieItemClickListener {

    class MovieDiffUtil: DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((Int) -> Unit) ?= null
    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val result = getItem(position)!!

        holder.bind(result)

        holder.binding.itemClick = this

        /*holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(result.id)
            }
        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListAdapter.Holder {
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
        val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(movieid = movieId)
        view.findNavController().navigate(action)
    }
}