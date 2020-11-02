package com.example.minitwitterapp.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.minitwitterapp.R
import com.example.minitwitterapp.model.remote.Data
import com.mikhaellopez.circularimageview.CircularImageView

class TwitterAdapter(private val data: List<Data>, private val context: Context) :
    RecyclerView.Adapter<TwitterAdapter.TwitterViewHolder>() {

    private val placeholderDrawable: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_person_24dp)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TwitterViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.tweets_item_layout, parent, false)
        return TwitterViewHolder(view)
    }

    override fun onBindViewHolder(holder: TwitterViewHolder, position: Int) {
        val userData: Data = data[position]
        Glide.with(context)
            .load(userData.profileImageUrl)
            .error(placeholderDrawable)
            .into(holder.twitterUserImage)
        holder.twitterUserNameTextView.text = userData.name
        holder.twitterUserHandleTextView.text = userData.handle
        holder.numLikesTextView.text = userData.favoriteCount.toString()
        holder.numRetweetsTextView.text = userData.retweetCount.toString()
        holder.tweetsTextView.text = userData.text
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class TwitterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val twitterUserImage: CircularImageView =
            itemView.findViewById(R.id.twitter_user_image_view)
        val twitterUserNameTextView: TextView =
            itemView.findViewById(R.id.twitter_username_text_view)
        val twitterUserHandleTextView: TextView =
            itemView.findViewById(R.id.twitter_user_handle_text_view)
        val numLikesTextView: TextView = itemView.findViewById(R.id.num_likes_text_view)
        val numRetweetsTextView: TextView = itemView.findViewById(R.id.num_retweets_text_view)
        val tweetsTextView: TextView = itemView.findViewById(R.id.tweets_text_view)

    }

}