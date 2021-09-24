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
) : RealmObject() {
    fun setData(
        id: String?, accountId: String?, puuid: String?, name: String?, profileIconId: Int?,
        revisionDate: Long?, summonerLevel: Long?
    ) {
        this.id = id
        this.accountId = accountId
        this.puuid = puuid
        this.name = name
        this.profileIconId = profileIconId
        this.revisionDate = revisionDate
        this.summonerLevel = summonerLevel
    }
    fun setNull(){
        this.apply {
            id = null
            accountId = null
            puuid = null
            name = null
            profileIconId = null
            revisionDate = null
            summonerLevel = null
        }
    }
}