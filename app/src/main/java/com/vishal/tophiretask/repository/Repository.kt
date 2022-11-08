package com.vishal.tophiretask.repository

import com.vishal.tophiretask.model.Welcome
import com.vishal.tophiretask.model.*
import com.vishal.tophiretask.utils.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getWelcomeMessage(key:String):Response<Welcome>{
        return RetrofitInstance.api.getWelcomeMessage(key)
    }
    suspend fun registerNewUser(userDetails:RequestNewUser):Response<NewUser>{
        return RetrofitInstance.api.registerNewUser(userDetails)
    }
    suspend fun loginUser(credentials:RequestLogin):Response<NewUser>{
        return RetrofitInstance.api.loginUser(credentials)
    }
    suspend fun tweet(key:String,tweet:RequestTweet):Response<Tweet>{
        return RetrofitInstance.api.tweet(key,tweet)
    }
    suspend fun getTweets(key:String):Response<ArrayList<Tweet>>{
        return RetrofitInstance.api.getTweets(key)
    }
    suspend fun updateTweet(id:String,tweet:RequestTweet):Response<RequestTweet>{
        return RetrofitInstance.api.updateTweet(tweet,id)
    }
}