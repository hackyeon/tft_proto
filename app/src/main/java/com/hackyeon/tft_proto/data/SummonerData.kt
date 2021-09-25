package com.hackyeon.tft_proto.data

import io.realm.RealmObject

open class SummonerData(
    var id: String? = null,
    var accountId: String? = null,
    var puuid: String? = null,
    var name: String? = null,
    var profileIconId: Int? = null,
    var revisionDate: Long? = null,
    var summonerLevel: Long? = null
) : RealmObject() {}