package com.loogika.mikroisp.app.payment.adapter.apiService

import com.loogika.mikroisp.app.payment.entity.ServiceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface apiPayment {

     @GET
     suspend fun getClientByName(@Url name:String): Response<ServiceResponse>
}