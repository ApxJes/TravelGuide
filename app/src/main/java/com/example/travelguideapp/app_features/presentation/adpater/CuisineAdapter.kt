package com.example.travelguideapp.app_features.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelguideapp.R
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import com.example.travelguideapp.databinding.CuisineLayoutBinding

class CuisineAdapter: RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>() {

    private var differCallBack = object: DiffUtil.ItemCallback<PlaceVo>() {
        override fun areItemsTheSame(
            oldItem: PlaceVo,
            newItem: PlaceVo
        ): Boolean {
            return oldItem.pageid == newItem.pageid
        }

        override fun areContentsTheSame(
            oldItem: PlaceVo,
            newItem: PlaceVo
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CuisineViewHolder {
        val binding = CuisineLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CuisineViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CuisineViewHolder,
        position: Int
    ) {
        val cuisine = differ.currentList[position]

        holder.binding.apply {
            txvCuisine.text = cuisine.title

            Glide.with(imgCuisine.context)
                .load(cuisine.thumbnail)
                .placeholder(R.drawable.tourism)
                .into(imgCuisine)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class CuisineViewHolder(val binding: CuisineLayoutBinding): RecyclerView.ViewHolder(binding.root)
}