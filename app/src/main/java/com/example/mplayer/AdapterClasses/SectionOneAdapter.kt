package com.example.mplayer.AdapterClasses

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mplayer.Activities.MyPlayerActivity
import com.example.mplayer.ModelClasses.SongsModel
import com.example.mplayer.MyExoplayer
import com.example.mplayer.databinding.Section1ItemViewBinding
import com.example.mplayer.databinding.SongsListItemViewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class SectionOneAdapter(private  val songIdList: List<String>) :
    RecyclerView.Adapter<SectionOneAdapter.SongViewHolder>() {

    class SongViewHolder(private val binding: Section1ItemViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bindData(songId : String){

            // binding.songTitleTxt.text = songId

            FirebaseFirestore.getInstance().collection("songs").document(songId).get()
                .addOnSuccessListener {
                    val song = it.toObject(SongsModel::class.java)
                    song?.apply {
                        binding.songTitleTxt.text = title
                        binding.songSubtitleTxt.text = subtitle
                        Glide.with(binding.songCoverImg).load(coverUrl)
                            .apply(
                                RequestOptions().transform(RoundedCorners(5))
                            )
                            .into(binding.songCoverImg)

                        // play the song
                        binding.root.setOnClickListener {
                            MyExoplayer.startPlaying(binding.root.context, song)
                            it.context.startActivity(Intent(it.context, MyPlayerActivity::class.java))
                        }

                    }
                }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = Section1ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }
}