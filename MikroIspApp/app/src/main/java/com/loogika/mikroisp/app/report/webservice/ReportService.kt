package com.loogika.mikroisp.app.report.webservice


import com.loogika.mikroisp.app.report.entity.ReportResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportService {
    @GET("findAll?institution_id=1")
    fun getReportDate(
        @Query("dni") dni:String,
        @Query("startDate") starDate:String,
        @Query("endDate") endDate: String
    ): Call<ReportResponse>
}