package com.hackyeon.tft_proto.data

import com.hackyeon.tft_proto.view_model.SummonerViewModel
import io.realm.Realm

object DataObject {
    const val BASE_URL = "https://kr.api.riotgames.com/"
    const val API_KEY = "RGAPI-8b99b6ae-5bd1-4e26-bf47-d507158f52de"
    const val GET_SUMMONER = "tft/summoner/v1/summoners/by-name/{summonerName}"

    lateinit var realm: Realm
    lateinit var viewModel: SummonerViewModel
}