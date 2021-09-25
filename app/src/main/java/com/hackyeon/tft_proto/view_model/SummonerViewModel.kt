package com.hackyeon.tft_proto.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackyeon.tft_proto.data.DataObject.realm
import com.hackyeon.tft_proto.data.SummonerData
import io.realm.Realm
import io.realm.RealmResults

class SummonerViewModel(
): ViewModel() {
    var summonerList = mutableListOf<SummonerData>()
    var liveSummonerList = MutableLiveData<MutableList<SummonerData>>(mutableListOf())

    fun insertSummoner(summonerData: SummonerData){
        if(summonerData.id != null){
            var isRepeat: Boolean = false
            for(i in summonerList){
                if(i.name == summonerData.name) isRepeat = true
            }
            if(!isRepeat){
                realm.executeTransaction {
                    it.copyToRealm(summonerData)
                }
                summonerList.add(summonerData)
                liveSummonerList.value = summonerList
            }
        }
    }

    fun deleteSummoner(name: String){
        realm.executeTransaction {
            var deleteSummoner = realm.where(SummonerData::class.java).equalTo("name", name).findFirst()
            if(deleteSummoner != null){
                deleteSummoner.deleteFromRealm()
                var tempIdx = -1
                for(i in summonerList.indices){
                    if(summonerList[i].name == name) tempIdx = i
                }
                summonerList.removeAt(tempIdx)
                liveSummonerList.value = summonerList
            }
        }
    }

    fun selectAllSummoner(list: RealmResults<SummonerData>){
        summonerList.clear()
        for(i in list){
            summonerList.add(i)
        }
        liveSummonerList.value = summonerList

    }

}