package com.vishal.tophiretask

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.tophiretask.databinding.ActivityWelcomeBinding
import com.vishal.tophiretask.functions.SharedPref
import com.vishal.tophiretask.repository.Repository
import com.vishal.tophiretask.tweet.TweetActivity
import com.vishal.tophiretask.utils.Constants
import com.vishal.tophiretask.utils.Constants.Companion.USER_DETAILS

class WelcomeActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var dataSharedPref : SharedPreferences
    lateinit var dataSharedPrefEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)

        dataSharedPref = getSharedPreferences(Constants.DATA, Context.MODE_PRIVATE)
        dataSharedPrefEditor = dataSharedPref.edit()

        val userData = SharedPref.getObject(dataSharedPref,USER_DETAILS)
        var intent:Intent? = null

        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let {
                    intent = Intent(this,TweetActivity::class.java)
                }
            }else{
                Toast.makeText(this, "failure", Toast.LENGTH_SHORT).show()
                intent = Intent(this,OnBoardingActivity::class.java)
            }
        })

        if (userData!= null){
            viewModel.getWelcomeMessage(userData.token)
            println(userData.token)
            println(userData)
            //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiNjM2YTk1N2QyOGJhODYxMDM3Y2I5ZWIzIiwiZW1haWwiOiJ0d2VldEBnbWFpbC5jb20iLCJpYXQiOjE2Njc5NDU0NjksImV4cCI6MTY2Nzk1MjY2OX0.hov6S-567ra5-4Ha2vwsmYxluo4kcO6vR5aNjWrRR-4
        }


        binding.letsTweetBtn.setOnClickListener {
            if (intent!= null && userData!= null){
                startActivity(intent)
                finish()
            }else if (intent == null && userData == null){
                val loginIntent = Intent(this,OnBoardingActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
        }

    }
}