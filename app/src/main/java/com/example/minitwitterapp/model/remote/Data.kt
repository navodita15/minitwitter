package com.example.minitwitterapp.model.remote

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_tweets_table")
data class Data(
    @NonNull @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "handle") var handle: String,
    @ColumnInfo(name = "favCount") var favoriteCount: Int,
    @ColumnInfo(name = "retweetCount") var retweetCount: Int,
    @ColumnInfo(name = "profileImgUrl") var profileImageUrl: String,
    @ColumnInfo(name = "text") var text: String
) {
    override fun toString(): String {
        return "Data(name=$name, handle=$handle, favoriteCount=$favoriteCount, retweetCount=$retweetCount, profileImageUrl=$profileImageUrl, text=$text)"
    }
}