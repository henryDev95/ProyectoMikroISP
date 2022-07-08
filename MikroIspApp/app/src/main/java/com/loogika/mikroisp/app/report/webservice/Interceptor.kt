package com.loogika.mikroisp.app.report.webservice

import com.loogika.mikroisp.app.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient

object Interceptor {
    fun getInterceptor(): OkHttpClient { // para añadir la cabecera en retrofil
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}