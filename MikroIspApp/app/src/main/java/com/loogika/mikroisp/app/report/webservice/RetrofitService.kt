package com.loogika.mikroisp.app.report.webservice

import com.loogika.mikroisp.app.interceptor.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    fun getRetrofil():Retrofit{
        val urlBase= "http://192.168.0.105/proyectos-web/adminwisp/web/app_dev.php/api/v1/payment/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }
}