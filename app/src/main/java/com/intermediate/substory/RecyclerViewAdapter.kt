package com.intermediate.substory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.db.StoryEntity
import com.intermediate.substory.databinding.ActivityItemBinding
import com.intermediate.substory.model.StoryModel

class RecyclerViewAdapter : PagingDataAdapter<StoryEntity, RecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(val binding: ActivityItemBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(data: StoryEntity)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ActivityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    private fun ViewHolder.bind(story: StoryEntity) {
        binding.tvNameUser.text = story.name
        Glide.with(itemView.context)
            .load(story.photoUrl)
            .into(binding.ivPhotoItem)

        itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(story)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryEntity, newItem: StoryEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}