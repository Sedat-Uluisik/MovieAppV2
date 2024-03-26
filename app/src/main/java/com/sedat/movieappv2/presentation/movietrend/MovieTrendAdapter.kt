package com.sedat.movieappv2.presentation.movietrend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.databinding.MovieListItemBinding

class MovieTrendAdapter : PagingDataAdapter<Result, MovieTrendAdapter.Holder>(MovieDiffUtil()) {

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

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(result.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrendAdapter.Holder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(private val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(result: Result){
            binding.apply {
                movie = result
                executePendingBindings()
            }
        }
    }
}