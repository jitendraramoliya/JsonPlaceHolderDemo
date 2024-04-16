package com.demo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.pojo.Photo
import com.demo.repository.ImgRepository
import com.demo.utils.AssetResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(private val imgRepository: ImgRepository) :
    ViewModel() {

    public val photoMutableList: LiveData<AssetResult<List<Photo>>>
        get() = imgRepository.photoMutableList

    fun getPhotoList(pageNo: Int) { // Fetching movie list
        viewModelScope.launch {
            imgRepository.getImgList(pageNo)
        }
    }

}