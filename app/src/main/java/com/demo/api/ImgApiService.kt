package com.thoughtctlapp.api

import com.demo.pojo.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgApiService {

    @GET("photos?_limit=15")
    suspend fun getImageList(@Query("_start")  pageNo:Int): Response<List<Photo>>

}