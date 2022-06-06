package com.loogika.mikroisp.app.device.apiService

import com.loogika.mikroisp.app.client.entity.clientResponse
import com.loogika.mikroisp.app.device.entity.DeviceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface deviceApi {
    @GET("findAll?institution_id=1")
    fun getAll(@Header("X-AUTH-TOKEN") auth:String) : Call<DeviceResponse> // para obtener la lista de respuesta
}