package com.loogika.mikroisp.app.interceptor

import okhttp3.OkHttpClient

object Interceptor {
    fun getInterceptor(): OkHttpClient { // para añadir la cabecera en retrofil
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}