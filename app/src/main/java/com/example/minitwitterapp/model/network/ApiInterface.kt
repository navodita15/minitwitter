package com.example.minitwitterapp.model.network

import com.example.minitwitterapp.model.remote.TwitterResponse
import retrofit2.http.GET

interface ApiInterface {
    @GET("/tweets")
    suspend fun getAllTweetsAsync(): TwitterResponse

    companion object {
        const val BASE_URL = "https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io"
    }
}
