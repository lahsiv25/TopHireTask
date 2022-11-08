package com.vishal.tophiretask.api

import okhttp3.Interceptor
import okhttp3.Response

class Interceptor:okhttp3.Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .build()
        return chain.proceed(request)
    }
}