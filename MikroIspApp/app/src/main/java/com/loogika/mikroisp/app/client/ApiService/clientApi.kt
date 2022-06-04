package com.loogika.mikroisp.app.client.ApiService

import com.loogika.mikroisp.app.client.entity.clientResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface clientApi {
    @GET("findAllClients?institution_id=1")
    fun getAll(@Header("X-AUTH-TOKEN") auth:String) : Call<clientResponse> // para obtener la lista de respuesta
}