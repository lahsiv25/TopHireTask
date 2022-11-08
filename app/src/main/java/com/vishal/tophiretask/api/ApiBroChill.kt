package com.vishal.tophiretask.api

import com.vishal.tophiretask.model.Welcome
import com.vishal.tophiretask.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiBroChill {

    @GET("welcome")
    suspend fun getWelcomeMessage(@Header("x-api-key") key:String):Response<Welcome>

    @POST("register")
    suspend fun registerNewUser(
        @Body userDetails:RequestNewUser
    ):Response<NewUser>

    @POST("login")
    suspend fun loginUser(
        @Body userDetails: RequestLogin
    ):Response<NewUser>

    @POST("tweets")
    suspend fun tweet(
        @Header("x-api-key") key:String,
        @Body tweet: RequestTweet
    ):Response<Tweet>

    @GET("tweets")
    suspend fun getTweets(@Header("x-api-key") key:String):Response<ArrayList<Tweet>>

    @PATCH("tweets/:id")
    suspend fun updateTweet(
        @Body tweet:RequestTweet,
        @Path("id") id:String
    ):Response<RequestTweet>

}