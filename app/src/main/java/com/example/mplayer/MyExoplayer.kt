package com.example.mplayer

import android.content.Context
import android.media.browse.MediaBrowser
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.MediaItemTransitionReason
import androidx.media3.exoplayer.ExoPlayer
import com.example.mplayer.ModelClasses.SongsModel


object MyExoplayer {

    private var exoPlayer: ExoPlayer? = null
    private var currentSong: SongsModel? = null

    fun getcurrentSong() : SongsModel?{
        return currentSong
    }

    fun getInstance(): ExoPlayer? {
        return exoPlayer
    }

    fun startPlaying(context: Context, song: SongsModel) {
        // We need to build it only one time
        if (exoPlayer == null) exoPlayer = ExoPlayer.Builder(context).build()

        if(currentSong!=song){

            currentSong = song
            currentSong?.url?.apply {

                // Build the media item.
                val mediaItem = MediaItem.fromUri(this)
                // Set the media item to be played.
                exoPlayer?.setMediaItem(mediaItem)
                // Prepare the player.
                exoPlayer?.prepare()
                // Start the playback.
                exoPlayer?.play()
        }


        }

    }
}