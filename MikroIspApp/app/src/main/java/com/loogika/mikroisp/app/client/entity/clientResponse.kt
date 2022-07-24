package com.loogika.mikroisp.app.client.entity

import com.google.gson.annotations.SerializedName

data class clientResponse(
       @SerializedName("totalRows") var totalRows: String,
       @SerializedName("entities")  var entities : List<Client>
)


data class clientResp(
    @SerializedName("entity") var client:Client,
    @SerializedName("status") var status:String
)

