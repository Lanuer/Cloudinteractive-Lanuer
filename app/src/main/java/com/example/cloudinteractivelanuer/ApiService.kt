package com.example.cloudinteractivelanuer

import com.example.cloudinteractivelanuer.model.DataModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/photos")
    fun getData(): Call<List<DataModel>>
}