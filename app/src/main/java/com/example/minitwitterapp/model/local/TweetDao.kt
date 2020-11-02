package com.example.minitwitterapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.minitwitterapp.model.remote.Data

@Dao
interface TweetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(data: Data)

    @Query("SELECT * FROM local_tweets_table where name GLOB '*' || :search|| '*' OR handle GLOB '*' || :search|| '*' OR text GLOB '*' || :search|| '*'")
    fun getSearchTweets(search: String): LiveData<List<Data>>

    @Query("SELECT COUNT(*) FROM local_tweets_table")
    fun getAllTweets(): LiveData<Int>
}