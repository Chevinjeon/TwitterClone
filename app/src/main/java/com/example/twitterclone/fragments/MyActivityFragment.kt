package com.example.twitterclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twitterclone.R
import com.example.twitterclone.adapters.TweetListAdapter
import com.example.twitterclone.listeners.TwitterListenerImpl
import com.example.twitterclone.utils.DATA_TWEETS
import com.example.twitterclone.utils.DATA_TWEET_USER_IDS
import com.example.twitterclone.utils.Tweet
import kotlinx.android.synthetic.main.fragment_my_activity.*

class MyActivityFragment : TwitterFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listener = TwitterListenerImpl(tweetList, currentUser, callback)

        tweetsAdapter = TweetListAdapter(userId!!, arrayListOf())
        tweetsAdapter?.setListener(listener)
        tweetList?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tweetsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        swipeRefresh.setOnRefreshListener {
            swipeRefresh.isRefreshing = false
            updateList()
        }
    }

    override fun updateList() {
        tweetList?.visibility = View.GONE
        val tweets = arrayListOf<Tweet>()

        firebaseDB.collection(DATA_TWEETS).whereArrayContains(DATA_TWEET_USER_IDS, userId!!).get()
            .addOnSuccessListener { list ->
                for (document in list.documents) {
                    val tweet = document.toObject(Tweet::class.java)
                    tweet?.let {
                        tweets.add(tweet)
                    }
                }

                val sortedList = tweets.sortedWith(compareByDescending { it.timestamp })
                tweetsAdapter?.updateTweets(sortedList)
                tweetList?.visibility = View.VISIBLE
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                tweetList?.visibility = View.VISIBLE
            }
    }

}