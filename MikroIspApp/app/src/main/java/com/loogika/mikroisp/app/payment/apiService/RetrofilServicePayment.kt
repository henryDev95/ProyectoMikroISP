package com.loogika.mikroisp.app.payment.apiService

import com.loogika.mikroisp.app.interceptor.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofilServicePayment {

    fun getRetrofitInvoice(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.100/proyectos-web/adminwisp/web/app_dev.php/api/v1/invoice/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }

    fun getRetrofitPayment(): Retrofit { // funcion de retrofil
        var urlBase = "http://192.168.0.100/proyectos-web/adminwisp/web/app_dev.php/api/v1/payment/"
        return Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Interceptor.getInterceptor())
            .build()
    }
}