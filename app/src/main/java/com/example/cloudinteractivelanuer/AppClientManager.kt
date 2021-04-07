package com.example.cloudinteractivelanuer

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppClientManager private constructor() {
    private val retrofit: Retrofit
    private var okHttpClient: OkHttpClient = OkHttpClient().newBuilder().build()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SecondActivity.url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    companion object {
        private val manager = AppClientManager()
        val client: Retrofit
            get() = manager.retrofit
    }

}