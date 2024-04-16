package com.demo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.DetailsActivity
import com.demo.R
import com.demo.databinding.ItemPhotoBinding
import com.demo.pojo.Photo
import com.squareup.picasso.Picasso


class PhotoAdapter(private val appContext: Context) :
    RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val list: MutableList<Photo> = mutableListOf()
    private val listFiltered: MutableList<Photo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.setItem(appContext, listFiltered.get(position))
    }

    fun submitList(movieList: List<Photo>) {
        // Adding movie in list
        list.addAll(movieList)
        listFiltered.addAll(movieList)
        notifyDataSetChanged()
    }

    fun setSearchText(searchText: String) {
        // filter movie base on text
        listFiltered.clear()
        if (searchText.isNullOrEmpty()) {
            listFiltered.addAll(list)
        } else {
            listFiltered.addAll(list.filter {
                it.title!!.startsWith(
                    searchText,
                    ignoreCase = true
                )
            })
        }
        notifyDataSetChanged()
    }

    class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setItem(appContext: Context, movieItem: Photo) {
            binding.tvName.text = movieItem.title

            movieItem.thumbnailUrl?.let {// Movie Image
                Picasso.get().load(it)
                    .placeholder(R.drawable.placeholder_for_missing_posters)
                    .error(R.drawable.placeholder_for_missing_posters)
                    .into(binding.ivPhoto)
            }

            binding.root.setOnClickListener{
                val intent = Intent(appContext, DetailsActivity::class.java).apply {
                    putExtra("title", movieItem.title)
                    putExtra("image", movieItem.thumbnailUrl)
                }
                appContext.startActivity(intent)
            }

        }



    }
}