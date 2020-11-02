package com.example.minitwitterapp.view

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.minitwitterapp.R
import com.example.minitwitterapp.model.remote.TwitterResponse
import com.example.minitwitterapp.viewmodel.TwitterViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var twitterViewModel: TwitterViewModel
    private lateinit var splashScreenLayout: RelativeLayout
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var searchLogoImageView: ImageView
    private lateinit var loadingImageView: ImageView
    private lateinit var sampleSearchTextView: TextView
    private lateinit var twitterRecyclerView: RecyclerView
    private lateinit var twitterAdapter: TwitterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        twitterViewModel = ViewModelProvider(this).get(TwitterViewModel::class.java)
        initializeViews()
        twitterViewModel.getTweets().observe(this, {
            putIntoLocalDb(it)

        })
        showSplashScreen()
        val dividerItemDecoration = DividerItemDecoration(
            applicationContext,
            LinearLayoutManager.VERTICAL
        )

        twitterRecyclerView.addItemDecoration(dividerItemDecoration)
        searchButton.setOnClickListener(searchListener)
    }

    private val searchListener: View.OnClickListener = View.OnClickListener {
        if (!TextUtils.isEmpty(searchEditText.text.toString())) {
            showLoading()
            twitterViewModel.search(searchEditText.text.toString()).observe(this, {
                if (it != null) {
                    if (it.isNotEmpty()) {
                        hideLogoAndSampleText()
                        twitterAdapter = TwitterAdapter(it, applicationContext)
                        twitterRecyclerView.adapter = twitterAdapter
                    } else {
                        hideLoading()
                        sampleSearchTextView.text = String.format(
                            getString(R.string.not_found),
                            searchEditText.text.toString()
                        )
                        Toast.makeText(applicationContext, NOT_FOUND, Toast.LENGTH_SHORT).show()
                    }

                }
            })
        } else {
            Toast.makeText(applicationContext, EMPTY_INVALID_STRING, Toast.LENGTH_SHORT).show()
        }
    }

    private fun putIntoLocalDb(twitterResponse: TwitterResponse?) {
        twitterViewModel.isDbFilled().observe(this, Observer {
            if (it <= 0) {
                twitterResponse?.data?.forEach { data ->
                    twitterViewModel.insert(data = data)
                }
            }
        })

    }

    private fun showSplashScreen() {
        val handler = Handler(mainLooper)
        handler.postDelayed({
            splashScreenLayout.visibility = View.GONE
        }, SPLASH_SCREEN_TIMEOUT)
    }

    private fun initializeViews() {
        splashScreenLayout = findViewById(R.id.splash_screen_layout)
        searchEditText = findViewById(R.id.search_edit_text)
        searchButton = findViewById(R.id.search_button)
        searchLogoImageView = findViewById(R.id.search_logo_image_view)
        loadingImageView = findViewById(R.id.search_loading)
        sampleSearchTextView = findViewById(R.id.sample_search_text_view)
        twitterRecyclerView = findViewById(R.id.tweets_recycler_view)
        hideLoading()
        twitterRecyclerView.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun hideLogoAndSampleText() {
        loadingImageView.visibility = View.GONE
        twitterRecyclerView.visibility = View.VISIBLE
    }

    private fun showLogoAndSampleText() {
        loadingImageView.visibility = View.VISIBLE
        twitterRecyclerView.visibility = View.GONE
    }

    private fun showLoading() {
        searchLogoImageView.visibility = View.GONE
        sampleSearchTextView.visibility = View.GONE
        loadingImageView.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        searchLogoImageView.visibility = View.VISIBLE
        sampleSearchTextView.visibility = View.VISIBLE
        loadingImageView.visibility = View.GONE
    }

    companion object {
        const val SPLASH_SCREEN_TIMEOUT: Long = 8000
        const val NOT_FOUND: String = "Query Not Found"
        const val EMPTY_INVALID_STRING: String = "Invalid/Empty String"
    }
}