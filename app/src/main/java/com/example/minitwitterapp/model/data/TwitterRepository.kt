package com.example.minitwitterapp.model.data

import androidx.lifecycle.LiveData
import com.example.minitwitterapp.model.local.TweetDao
import com.example.minitwitterapp.model.network.ApiInterface
import com.example.minitwitterapp.model.remote.Data
import com.example.minitwitterapp.model.remote.TwitterResponse

class TwitterRepository(private val apiInterface: ApiInterface, private val tweetDao: TweetDao) {

    private var isFilled: Boolean = false

    suspend fun insert(data: Data) {
        tweetDao.insert(data)
        isFilled = true
    }

    suspend fun getAllTweets(): TwitterResponse {
        return apiInterface.getAllTweetsAsync()
    }

    fun getSearchedTweets(searchString: String): LiveData<List<Data>> {
        return tweetDao.getSearchTweets(searchString)
    }

    fun getAllPresentTweets(): LiveData<Int> {
        return tweetDao.getAllTweets()
    }

    fun isDbFilled(): Boolean {
        return isFilled
    }

}