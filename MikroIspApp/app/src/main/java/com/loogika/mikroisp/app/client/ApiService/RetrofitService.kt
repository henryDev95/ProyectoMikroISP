package com.loogika.mikroisp.app.client.ApiService

import com.loogika.mikroisp.app.interceptor.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    // 34.238.198.216 ---> direccion ip del servidor
    fun getRetrofitClient(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.106/proyectos-web/adminwisp/web/app_dev.php/api/v1/client/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }

    fun getRetrofitService(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.106/proyectos-web/adminwisp/web/app_dev.php/api/v1/service/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }
}