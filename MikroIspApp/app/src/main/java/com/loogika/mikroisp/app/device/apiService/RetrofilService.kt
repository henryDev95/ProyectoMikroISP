package com.loogika.mikroisp.app.device.apiService

import com.loogika.mikroisp.app.interceptor.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofilService {

    fun getRetrofit(): Retrofit { // funcion de retrofil
        var urlBase = "http://34.238.198.216/proyectos-web/adminwisp/web/app_dev.php/api/v1/device/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }

    fun getRetrofitService(): Retrofit { // funcion de retrofil service con device
        var urlBase = "http://34.238.198.216/proyectos-web/adminwisp/web/app_dev.php/api/v1/servicedevice/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }


}