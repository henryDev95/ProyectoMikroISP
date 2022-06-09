package com.loogika.mikroisp.app.client.ApiService

import com.loogika.mikroisp.app.client.entity.Client
import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.clientResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface clientApi {
    @GET("findAllClients?institution_id=1")
    fun getAll() : Call<clientResponse> // para obtener la lista de respuesta

    @POST("findAllClients?institution_id=1")
    fun createClient(@Body client: ClientPost): Response <clientResponse>

}