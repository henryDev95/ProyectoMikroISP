package com.loogika.mikroisp.app.client.ApiService

import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.clientResp
import com.loogika.mikroisp.app.client.entity.clientResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface clientApi {
    @GET("findAllClients?institution_id=1")
    fun getAll() : Call<clientResponse> // para obtener la lista de respuesta

    @POST("newClient?institution_id=1")
    fun createClient(@Body client:ClientPost): Call<clientResp>
}