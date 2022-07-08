package com.loogika.mikroisp.app.report.webservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    val urlBase= "http://192.168.0.107/proyectos-web/adminwisp/web/app_dev.php/api/v1/payment/"
    fun getRetrofil():Retrofit{
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }
}