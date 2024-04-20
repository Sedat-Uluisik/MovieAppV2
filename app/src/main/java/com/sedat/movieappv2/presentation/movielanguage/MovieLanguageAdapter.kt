package com.sedat.movieappv2.presentation.movielanguage

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sedat.movieappv2.R
import com.sedat.movieappv2.data.remote.model.LanguageItem
import com.sedat.movieappv2.data.remote.model.Result
import com.sedat.movieappv2.databinding.ItemLayoutLanguageBinding
import com.sedat.movieappv2.databinding.MovieListItemBinding
import com.sedat.movieappv2.interfaces.MovieItemClickListener

class MovieLanguageAdapter : ListAdapter<LanguageItem, MovieLanguageAdapter.Holder>(MovieDiffUtil()) {

    class MovieDiffUtil: DiffUtil.ItemCallback<LanguageItem>(){
        override fun areItemsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean {
            return oldItem.isSelect == newItem.isSelect && oldItem.iso6391 == newItem.iso6391
        }

        override fun areContentsTheSame(oldItem: LanguageItem, newItem: LanguageItem): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClickListener: ((String) -> Unit) ={}
    fun setOnItemClickListener(listener: (String) -> Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val result = getItem(position)!!

        holder.bind(result)

        holder.binding.linearLayout.setOnClickListener {
            onItemClickListener.invoke(result.iso6391)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemLayoutLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    class Holder(val binding: ItemLayoutLanguageBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(language: LanguageItem){
            binding.apply {
                txtLanguage.text = language.iso6391

                if(language.isSelect){
                    linearLayout.setBackgroundResource(R.drawable.language_item_selected_background)
                    txtLanguage.setTextColor(Color.WHITE)
                }else{
                    linearLayout.setBackgroundResource(R.drawable.language_item_background)
                    txtLanguage.setTextColor(Color.BLACK)
                }
            }
        }
    }
}