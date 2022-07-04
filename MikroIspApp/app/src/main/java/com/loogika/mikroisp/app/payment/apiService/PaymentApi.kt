package com.loogika.mikroisp.app.payment.apiService

import com.loogika.mikroisp.app.client.entity.ClientPost
import com.loogika.mikroisp.app.client.entity.clientResp
import com.loogika.mikroisp.app.payment.entity.PaymentPost
import com.loogika.mikroisp.app.payment.entity.PaymentResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PaymentApi {

    // para obtener la lista de los clientes pendientes de pago
    @GET("findPending?institution_id=1")
    fun getAll():Call<PaymentResponse>// para obtener la lista de respuesta

    // para guardar el pago
    @POST("newPayment?institution_id=1")
    fun createPayment(@Body payment: PaymentPost): Call<clientResp>

}