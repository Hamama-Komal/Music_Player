package com.example.mplayer.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mplayer.MyExoplayer
import com.example.mplayer.R
import com.example.mplayer.databinding.ActivityMyPlayerBinding

class MyPlayerActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMyPlayerBinding
    lateinit var exoPlayer: ExoPlayer

    var playerListener = object : Player.Listener {
        override fun onIsPlayingChanged(isPlaying : Boolean){
            super.onIsPlayingChanged(isPlaying)
            showGif(isPlaying)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MyExoplayer.getcurrentSong()?.apply {
            binding.songTitleTxt.text = title
            binding.songSubtitleTxt.text = subtitle

            Glide.with(binding.songCoverImg).load(coverUrl)
                .circleCrop()
                .into(binding.songCoverImg)

            Glide.with(binding.songCoverGif).load(R.drawable.media_playing)
                .circleCrop()
                .into(binding.songCoverGif)
            exoPlayer = MyExoplayer.getInstance()!!
            binding.playerView.showController()
            binding.playerView.player = exoPlayer

            exoPlayer.addListener(playerListener)

        }

    }

    fun showGif(show : Boolean){
        if(show){
            binding.songCoverGif.visibility = View.VISIBLE
        }
        else{
            binding.songCoverGif.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.removeListener(playerListener)
    }
}