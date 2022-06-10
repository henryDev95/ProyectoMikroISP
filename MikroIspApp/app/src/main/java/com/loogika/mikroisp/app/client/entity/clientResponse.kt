package com.loogika.mikroisp.app.client.entity

import com.google.gson.annotations.SerializedName

data class clientResponse(
    val totalRows : String,
    val entities: List<Client>
)


data class clientResp(
    @SerializedName("entity") var client:Client,
    @SerializedName("status") var status:String
)

