package com.loogika.mikroisp.app.client.ApiService

import com.loogika.mikroisp.app.client.entity.*
import com.loogika.mikroisp.app.client.service.entity.ServiceDevicePost
import com.loogika.mikroisp.app.client.service.entity.ServiceDeviceResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface clientApi {
    //para listar todos los clientes
    @GET("findAllClients?institution_id=1")
    fun getAll() : Call<clientResponse> // para obtener la lista de respuesta

    // para ingresar un nuevo cliente
    @POST("newClient?institution_id=1")
    fun createClient(@Body client:ClientPost): Call<clientResp>

   // para editar el cliente
    @POST("{id}/editClient?institution_id=1")
    fun editClient(@Body client:ClientPost,@Path("id") id:Int): Call<clientResp>

    // para ingresar el servicio de un cliente
    @POST("newService?institution_id=1")
    fun createServiceClient(@Body service:ServicePost): Call<ServiceResponse>

    // para ingresar el equipo por servicio
    @POST("newAssignDevice?institution_id=1")
    fun createServiceDeviceAssign(@Body serviceDevice:ServiceDevicePost): Call<ServiceDeviceResponse>
}