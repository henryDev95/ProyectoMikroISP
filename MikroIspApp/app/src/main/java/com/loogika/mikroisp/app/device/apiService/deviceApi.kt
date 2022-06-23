package com.loogika.mikroisp.app.device.apiService

import com.loogika.mikroisp.app.client.entity.clientResp
import com.loogika.mikroisp.app.device.entity.DevicePost
import com.loogika.mikroisp.app.device.entity.DeviceResp
import com.loogika.mikroisp.app.device.entity.DeviceResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface deviceApi {
    @GET("findAll?institution_id=1")
    fun getAll() : Call<DeviceResponse> // para obtener la lista de respuesta

    @POST("newDevice?institution_id=1")
    fun createDevice(@Body device: DevicePost): Call<DeviceResp>
}