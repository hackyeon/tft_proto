package com.hackyeon.tft_proto.service

import com.hackyeon.tft_proto.data.DataObject.GET_SUMMONER
import com.hackyeon.tft_proto.data.SummonerData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface RetrofitService {

    @GET(GET_SUMMONER)
    fun getSummoner(
        @Path("summonerName")summonerName: String
    ): Call<SummonerData>

}