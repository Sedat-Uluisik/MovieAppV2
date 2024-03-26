package com.sedat.movieappv2.presentation.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.data.remote.model.imagemodel.Backdrop
import com.sedat.movieappv2.databinding.MovieImagesItemLayoutBinding

class MovieImagesAdapter: ListAdapter<Backdrop, MovieImagesAdapter.Holder>(MovieImagesDiffUtil()) {

    class MovieImagesDiffUtil: DiffUtil.ItemCallback<Backdrop>(){
        override fun areItemsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem.filePath == newItem.filePath
        }

        override fun areContentsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            MovieImagesItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(private val binding: MovieImagesItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(backdrop: Backdrop){
            binding.apply {
                imageUrl = backdrop.getImageUrl()
                executePendingBindings()
            }
        }
    }

}