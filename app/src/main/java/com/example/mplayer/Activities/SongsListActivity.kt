package com.example.mplayer.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mplayer.AdapterClasses.SongListAdapter
import com.example.mplayer.ModelClasses.CategoryModel
import com.example.mplayer.R
import com.example.mplayer.databinding.ActivitySongsListBinding

class SongsListActivity : AppCompatActivity() {

    companion object{
        lateinit var category: CategoryModel
    }
    private lateinit var binding: ActivitySongsListBinding
    lateinit var songListAdapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameTxt.text = category.name
        Glide.with(binding.coverImg).load(category.coverUrl)
            .apply(
                RequestOptions().transform(RoundedCorners(32))
            )
            .into(binding.coverImg)

        setSongListRecycler()
    }

    fun setSongListRecycler(){
        songListAdapter = SongListAdapter(category.songs)
        binding.songListRecycler.layoutManager = LinearLayoutManager(this)
        binding.songListRecycler.adapter = songListAdapter

    }
}