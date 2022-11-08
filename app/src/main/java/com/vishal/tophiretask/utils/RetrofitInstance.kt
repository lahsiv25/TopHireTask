package com.vishal.tophiretask.utils

import com.vishal.tophiretask.api.ApiBroChill
import com.vishal.tophiretask.api.Interceptor
import com.vishal.tophiretask.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor())
    }.build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:ApiBroChill by lazy {
        retrofit.create(ApiBroChill::class.java)
    }
}