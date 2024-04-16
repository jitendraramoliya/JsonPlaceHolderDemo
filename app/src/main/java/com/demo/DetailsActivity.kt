package com.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.databinding.ActivityPhotoDetailsBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {

        val title = intent.extras?.getString("title")
        val thumbnail = intent.extras?.getString("image")

        if (title?.isNotEmpty() == true) {
            binding.tvName.text = title
            thumbnail?.let {// Movie Image
                Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .error(R.drawable.placeholder_for_missing_posters)
                    .into(binding.ivPhoto)
            }
        }

        binding.tvBack.setOnClickListener {
            finish()
        }

    }

}