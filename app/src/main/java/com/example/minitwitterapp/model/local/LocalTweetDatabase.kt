package com.example.minitwitterapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.minitwitterapp.model.remote.Data

@Database(entities = [Data::class], version = 1, exportSchema = false)
abstract class LocalTweetDatabase : RoomDatabase() {
    abstract fun tweetDao(): TweetDao

    companion object {
        @Volatile
        private var INSTANCE: LocalTweetDatabase? = null

        fun getDatabase(context: Context): LocalTweetDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalTweetDatabase::class.java,
                    "local_tweet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
