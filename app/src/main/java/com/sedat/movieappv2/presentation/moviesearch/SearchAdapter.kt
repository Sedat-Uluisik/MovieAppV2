package com.sedat.movieappv2.presentation.moviesearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sedat.movieappv2.data.remote.model.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.databinding.ItemLayoutSearchBinding
import com.sedat.movieappv2.interfaces.SearchItemClickListener

class SearchAdapter: ListAdapter<Result, SearchAdapter.Holder>(SearchDiffUtil()), SearchItemClickListener {

    class SearchDiffUtil : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutSearchBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.Holder, position: Int) {
        holder.bind(getItem(position))

        holder.binding.itemClick = this
    }

    class Holder(var binding: ItemLayoutSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(_result: Result) = binding.apply{
            result = _result
        }
    }

    override fun onSearchItemClick(view: View, movieId: Int) {
        //val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movie_id)
        //view.findNavController().navigate(action)
    }

}