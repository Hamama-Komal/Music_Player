package com.example.mplayer.AdapterClasses

import android.content.Intent
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mplayer.Activities.SongsListActivity
import com.example.mplayer.ModelClasses.CategoryModel
import com.example.mplayer.databinding.CategoryItemViewBinding

class CategoryAdapter(private val categoryList : List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CatViewHolder>() {

    class CatViewHolder(private val binding: CategoryItemViewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bindData(category : CategoryModel){
            binding.nameTxt.text = category.name
            Glide.with(binding.coverImg).load(category.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(32))
                )
                .into(binding.coverImg)

           Log.i("MYSONGS", category.songs.size.toString())

            // Start SongList Activity
            val context = binding.root.context
            binding.root.setOnClickListener{
                SongsListActivity.category = category
                context.startActivity(Intent(context, SongsListActivity::class.java))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = CategoryItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return categoryList.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bindData(categoryList[position])

    }
}