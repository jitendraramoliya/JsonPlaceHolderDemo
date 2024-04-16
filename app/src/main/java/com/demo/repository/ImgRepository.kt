package com.demo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.demo.pojo.Photo
import com.demo.utils.AssetResult
import com.thoughtctlapp.api.ImgApiService
import javax.inject.Inject

open class ImgRepository @Inject constructor(private val imgApiService: ImgApiService) {

    private var _photoMutableList = MutableLiveData<AssetResult<List<Photo>>>()
    public val photoMutableList: LiveData<AssetResult<List<Photo>>>
        get() = _photoMutableList

    suspend fun getImgList(pageNo: Int) {
        val response = imgApiService.getImageList(pageNo)
        if (response.isSuccessful && response.body() != null) {
            println(response.body().toString())
            _photoMutableList.postValue(AssetResult.Success(response.body()!!))
        }else{
            _photoMutableList.postValue(AssetResult.Error("Something went wrong"))
        }
    }

}