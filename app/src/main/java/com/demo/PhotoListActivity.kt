package com.demo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.adapter.PhotoAdapter
import com.demo.databinding.ActivityPhotoListBinding
import com.demo.utils.AssetResult
import com.demo.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.security.AccessController.getContext


@AndroidEntryPoint
class PhotoListActivity : AppCompatActivity() {

    lateinit var adapterMovie: PhotoAdapter
    private lateinit var binding: ActivityPhotoListBinding
    private val photoViewModel: PhotoViewModel by viewModels()
    private var pageNo = 1
    private var isLastPage = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        handleObserver()
    }


    private fun initViews() {

        // adding movie adapter
//        binding.rvPhoto.layoutManager = getLayoutManager()
        binding.rvPhoto.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        adapterMovie = PhotoAdapter(this)
        binding.rvPhoto.adapter = adapterMovie
        binding.rvPhoto.addOnScrollListener(recyclerViewOnScrollListener)

        binding.tvBack.setOnClickListener {
            finish()
        }

        binding.tvSearch.setOnClickListener {
            binding.rltSearch.visibility = View.VISIBLE
            binding.rltTitle.visibility = View.GONE
        }

        binding.tvCancel.setOnClickListener {
            binding.etSearch.setText("")
            adapterMovie.setSearchText("")
            binding.rltSearch.visibility = View.GONE
            binding.rltTitle.visibility = View.VISIBLE
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(srt: Editable?) {
                if (srt.toString().length > 2) {
                    adapterMovie.setSearchText(srt.toString())
                }
            }

        })

        photoViewModel.getPhotoList(pageNo)

    }

    private fun handleObserver() { // Getting movie list
        photoViewModel.photoMutableList.observe(this) {
            when (it) {
                is AssetResult.Error -> {
                    isLoading = false
                }

                is AssetResult.Loading -> {
                }

                is AssetResult.Success -> {
                    val photoResponse = it.data!!
                    isLoading = false
                    isLastPage = false
                    print(photoResponse.toString())
                    adapterMovie.submitList(photoResponse)
                }
            }
        }
    }

//    private fun getLayoutManager(): RecyclerView.LayoutManager {
//        val orientation = resources.configuration.orientation
//        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3
//        binding.rvPhoto.addItemDecoration(GridSpacingItemDecoration(spanCount))
//        return GridLayoutManager(this, spanCount)
//    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() { // Pagination
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.findLastVisibleItemPosition()
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if (!isLoading && !isLastPage) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 5 /*&& totalItemCount >= PAGE_SIZE*/) {
                    pageNo += 1
                    isLoading = true
                    photoViewModel.getPhotoList(pageNo)
                }
            }
        }
    }

}