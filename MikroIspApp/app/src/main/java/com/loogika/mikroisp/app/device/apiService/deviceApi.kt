package com.loogika.mikroisp.app.device.apiService

import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.clientResp
import com.loogika.mikroisp.app.device.entity.*
import retrofit2.Call
import retrofit2.http.*

interface deviceApi {
    // listar todos device
    @GET("findAll?institution_id=1")
    fun getAll() : Call<DeviceResponse> // para obtener la lista de respuesta

    // crear un nuevo dispositivo
    @POST("newDevice?institution_id=1")
    fun createDevice(@Body device: DevicePost): Call<DeviceResp>

    // para editar el equipo
    @POST("{id}/editDevice?institution_id=1")
    fun editDevice(@Body device: DeviceEdit, @Path("id") id:Int): Call<DeviceResp>
}