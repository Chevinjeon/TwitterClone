package com.example.twitterclone.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.twitterclone.R
import com.example.twitterclone.fragments.HomeFragment
import com.example.twitterclone.fragments.MyActivityFragment
import com.example.twitterclone.fragments.SearchFragment
import com.example.twitterclone.fragments.TwitterFragment
import com.example.twitterclone.listeners.HomeCallback
import com.example.twitterclone.utils.DATA_USERS
import com.example.twitterclone.utils.loadUrl
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.twitterclone.utils.User
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeCallback {

    private var sectionsPagerAdapter: SectionPageAdapter? = null
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val myActivityFragment = MyActivityFragment()
    private var userId = FirebaseAuth.getInstance().currentUser?.uid
    private var user: User? = null
    private var imageUrl: String? = null
    private var currentFragment: TwitterFragment = homeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        sectionsPagerAdapter = SectionPageAdapter(supportFragmentManager)

        container.adapter = sectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        tabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {

                    // first tab
                    0 -> {
                        titleBar.visibility = View.VISIBLE
                        titleBar.text = "Home"
                        searchBar.visibility = View.GONE
                        currentFragment = homeFragment
                    }

                    // second tab
                    1 -> {
                        titleBar.visibility = View.GONE
                        searchBar.visibility = View.VISIBLE
                        currentFragment = searchFragment
                    }

                    // third tab
                    2 -> {

                        titleBar.visibility = View.VISIBLE
                        titleBar.text = "My Activity"
                        searchBar.visibility = View.GONE
                        currentFragment = myActivityFragment
                    }
                }
            }
        })

        logo.setOnClickListener { view ->
            startActivity(ProfileActivity.newIntent(this))
        }

        fab.setOnClickListener {
            startActivity(TweetActivity.newIntent(this, userId, user?.username))
        }

        homeProgressLayout.setOnTouchListener { v, event -> true }

        search.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFragment.newHashtag(v?.text.toString())
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        userId = FirebaseAuth.getInstance().currentUser?.uid
        if(userId == null) {
            startActivity(LoginActivity.newIntent(this))
            finish()
            }
            populate()
        }


    override fun onUserUpdated() {
        populate()
    }

    override fun onRefresh() {
        currentFragment.updateList()
    }

    fun populate() {
        homeProgressLayout.visibility = View.VISIBLE
        firebaseDB.collection(DATA_USERS).document(userId!!).get()
            .addOnSuccessListener { documentSnapshot ->
                homeProgressLayout.visibility = View.GONE
                user = documentSnapshot.toObject(User::class.java)
                user?.imageUrl?.let {
                    logo.loadUrl(it, R.drawable.logo)
                }
                updateFragmentUser()
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                finish()
            }
    }

    fun updateFragmentUser() {
        homeFragment.setUser(user)
        searchFragment.setUser(user)
        myActivityFragment.setUser(user)
        currentFragment.updateList()
    }

    inner class SectionPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> homeFragment
                1 -> searchFragment
                else -> myActivityFragment
            }
        }

        override fun getCount() = 3

    }

    companion object {
        fun newIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }
}