package com.example.minitwitterapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(ApiInterface.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
