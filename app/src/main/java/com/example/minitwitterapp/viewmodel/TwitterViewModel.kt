package com.example.minitwitterapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.minitwitterapp.model.data.TwitterRepository
import com.example.minitwitterapp.model.local.LocalTweetDatabase
import com.example.minitwitterapp.model.network.ApiInterface
import com.example.minitwitterapp.model.network.getRetrofit
import com.example.minitwitterapp.model.remote.Data
import com.example.minitwitterapp.model.remote.TwitterResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TwitterViewModel(application: Application) : AndroidViewModel(application) {

    private val twitterRepository: TwitterRepository
    private lateinit var isDbEmpty : LiveData<Int>


    init {
        val tweetDao = LocalTweetDatabase.getDatabase(application).tweetDao()
        twitterRepository =
            TwitterRepository(getRetrofit().create(ApiInterface::class.java), tweetDao)
    }


    fun getTweets(): LiveData<TwitterResponse> {
        return liveData(Dispatchers.IO) {
            val response = twitterRepository.getAllTweets()
            isDbEmpty = twitterRepository.getAllPresentTweets()
            emit(response)
        }
    }

    fun insert(data: Data) = viewModelScope.launch(Dispatchers.IO) {
        twitterRepository.insert(data)
    }

    fun search(searchString: String): LiveData<List<Data>> {
        return twitterRepository.getSearchedTweets(searchString)
    }

    fun isDbFilled(): LiveData<Int>  {
        return isDbEmpty
    }
}