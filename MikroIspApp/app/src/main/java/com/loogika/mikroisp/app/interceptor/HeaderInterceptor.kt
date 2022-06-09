package com.loogika.mikroisp.app.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-AUTH-TOKEN" , "123456henry")
            .build()
        return  chain.proceed(request)
    }
}