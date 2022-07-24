package com.loogika.mikroisp.app.user.serviceApi

import com.loogika.mikroisp.app.client.entity.ServiceResponse
import com.loogika.mikroisp.app.client.service.entity.ServiceEdit
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // para edit el servicio
    @POST("Autentification?institution_id=1")
    fun authentificationUser(@Body users: UserPost): Call<UserResponse>
}