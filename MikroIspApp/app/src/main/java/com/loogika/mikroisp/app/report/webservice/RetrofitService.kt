package com.loogika.mikroisp.app.report.webservice

import com.loogika.mikroisp.app.interceptor.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    // 34.238.198.216 ---> direccion ip del servidor
    fun getRetrofil():Retrofit{
        val urlBase= "http://34.238.198.216/proyectos-web/adminwisp/web/app_dev.php/api/v1/payment/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }
}