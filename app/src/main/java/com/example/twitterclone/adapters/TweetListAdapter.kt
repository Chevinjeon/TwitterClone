package com.example.twitterclone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.R
import com.example.twitterclone.listeners.TweetListener
import com.example.twitterclone.utils.Tweet
import com.example.twitterclone.utils.getDate
import com.example.twitterclone.utils.loadUrl

class TweetListAdapter(val userId: String, val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetListAdapter.TweetViewHolder>() {

    private var listener: TweetListener? = null

    fun setListener(listener: TweetListener?) {
        this.listener = listener
    }

    fun updateTweets(newTweets: List<Tweet>) {
        tweets.clear()
        tweets.addAll(newTweets)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = TweetViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_tweet, parent, false)
    )

    override fun getItemCount() = tweets.size

    override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
        holder.bind(userId, tweets[position], listener)
    }

    class TweetViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val layout = v.findViewById<ViewGroup>(R.id.tweetLayout)
        private val username = v.findViewById<TextView>(R.id.tweetUsername)
        private val text = v.findViewById<TextView>(R.id.tweetText)
        private val image = v.findViewById<ImageView>(R.id.tweetImage)
        private val date = v.findViewById<TextView>(R.id.tweetDate)
        private val like = v.findViewById<ImageView>(R.id.tweetLike)
        private val likeCount = v.findViewById<TextView>(R.id.tweetLikeCount)
        private val retweet = v.findViewById<ImageView>(R.id.tweetRetweet)
        private val retweetCount = v.findViewById<TextView>(R.id.tweetRetweetCount)

        fun bind(userId: String, tweet: Tweet, listener: TweetListener?) {
            username.text = tweet.username
            text.text = tweet.text
            if(tweet.imageUrl.isNullOrEmpty()) {
                image.visibility = View.GONE
            } else {
                image.visibility = View.VISIBLE
                image.loadUrl(tweet.imageUrl)
            }
            date.text = getDate(tweet.timestamp)

            likeCount.text = tweet.likes?.size.toString()
            retweetCount.text = tweet.userIds?.size?.minus(1).toString()

            layout.setOnClickListener { listener?.onLayoutClick(tweet) }
            like.setOnClickListener { listener?.onLike(tweet) }
            retweet.setOnClickListener { listener?.onRetweet(tweet) }

            if(tweet.likes?.contains(userId) == true) {
                like.setImageDrawable(ContextCompat.getDrawable(like.context, R.drawable.like))
            } else {
                like.setImageDrawable(ContextCompat.getDrawable(like.context, R.drawable.like_inactive))
            }

            if(tweet.userIds?.get(0).equals(userId)) {
                retweet.setImageDrawable(ContextCompat.getDrawable(like.context, R.drawable.original))
                retweet.isClickable = false
            } else if (tweet.userIds?.contains(userId) == true) {
                retweet.setImageDrawable(ContextCompat.getDrawable(like.context, R.drawable.retweet))
            } else {
                retweet.setImageDrawable(ContextCompat.getDrawable(like.context, R.drawable.retweet_inactive))
            }
        }
    }
}