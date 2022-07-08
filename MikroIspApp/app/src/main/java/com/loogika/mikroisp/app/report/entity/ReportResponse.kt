package com.loogika.mikroisp.app.report.entity

import com.google.gson.annotations.SerializedName

data class ReportResponse(
    @SerializedName("totalRows") val cantidadRegistro: Int,
    @SerializedName("entities") val report: List<Report>
)
