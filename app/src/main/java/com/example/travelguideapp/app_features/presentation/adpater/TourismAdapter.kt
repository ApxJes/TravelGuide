package com.example.travelguideapp.app_features.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.travelguideapp.R
import com.example.travelguideapp.app_features.domain.model.PlaceVo
import com.example.travelguideapp.databinding.DestinationLayoutBinding

class TourismAdapter : RecyclerView.Adapter<TourismAdapter.TourismViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<PlaceVo>() {
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
    ): TourismViewHolder {
        val binding = DestinationLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TourismViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TourismViewHolder,
        position: Int
    ) {
        val place = differ.currentList[position]

        holder.binding.apply {
            txvPlaceName.text = place.title

            Glide.with(imgPlace.context)
                .load(place.thumbnail)
                .placeholder(R.drawable.tourism)
                .into(imgPlace)

            root.setOnClickListener {
                onClick?.invoke(place)
            }

            imgSave.setOnClickListener {}
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onClick: ((PlaceVo) -> Unit)? = null
    fun setOnClickListener(listener: (PlaceVo) -> Unit) {
        onClick = listener
    }

    inner class TourismViewHolder(val binding: DestinationLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}