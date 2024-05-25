package com.example.mplayer.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.mplayer.AdapterClasses.CategoryAdapter
import com.example.mplayer.AdapterClasses.SectionOneAdapter
import com.example.mplayer.ModelClasses.CategoryModel
import com.example.mplayer.MyExoplayer
import com.example.mplayer.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Category Section

        getCategory()

        // Section One

        setSections("section_1", binding.mainSection1, binding.recyclerSection1, binding.section1Title)

        setSections("section_2", binding.mainSection2, binding.recyclerSection2, binding.section2Title)

        setSections("section_3", binding.mainSection3, binding.recyclerSection3, binding.section3Title)


    }

    override fun onResume() {
        super.onResume()
        showPlayingSong()
    }

    fun showPlayingSong(){

        binding.playerView.setOnClickListener{

            startActivity(Intent(this, MyPlayerActivity::class.java))
        }

        MyExoplayer.getcurrentSong()?.let {
            binding.playerView.visibility = View.VISIBLE
            binding.songTitleTxt.text= "Now Playing: ${it.title}"
            Glide.with(binding.songCoverImg).load(it.coverUrl)
                .apply(
                    RequestOptions().transform(RoundedCorners(10))
                )
                .into(binding.songCoverImg)

        } ?: run{
            binding.playerView.visibility = View.GONE
        }
    }

    fun getCategory(){

        FirebaseFirestore.getInstance().collection("category").get().addOnSuccessListener {
            val categoryList = it.toObjects(CategoryModel::class.java)
            setCategoryRecycler(categoryList)
        }
    }

    fun setCategoryRecycler(categoryList : List<CategoryModel>){
        categoryAdapter = CategoryAdapter(categoryList)
        binding.recyclerCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.recyclerCategories.adapter = categoryAdapter

    }


   fun setSections(id: String, mainSection: LinearLayout, recyclerView : RecyclerView, title : TextView){
       FirebaseFirestore.getInstance().collection("sections").document(id)
           .get().addOnSuccessListener {
               val section = it.toObject(CategoryModel::class.java)
               section?.apply {
                   mainSection.visibility = View.VISIBLE
                   title.text = name
                   recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                   recyclerView.adapter = SectionOneAdapter(songs)
                   mainSection.setOnClickListener {
                       SongsListActivity.category = section
                       startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                     }
               }
           }
   }
}
