package com.hackyeon.tft_proto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.hackyeon.tft_proto.data.DataObject
import com.hackyeon.tft_proto.data.DataObject.API_KEY
import com.hackyeon.tft_proto.data.DataObject.BASE_URL
import com.hackyeon.tft_proto.data.DataObject.realm
import com.hackyeon.tft_proto.data.DataObject.viewModel
import com.hackyeon.tft_proto.data.SummonerData
import com.hackyeon.tft_proto.databinding.ActivityMainBinding
import com.hackyeon.tft_proto.service.RetrofitService
import com.hackyeon.tft_proto.view_model.SummonerViewModel
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService
    private lateinit var binding: ActivityMainBinding
    private var compositeDisposable = CompositeDisposable()
    var liveSummonerData = MutableLiveData(SummonerData())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun initView() {
        dataBinding()
        observerSetting()
        retrofitSetting()
        realmSetting()
        selectAllSummoner()
    }


    private fun dataBinding() {
        // dataBinding, liveData
        // binding 설정 및 setContentView라 onCreate에서 가장먼저 실행되야함
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity).get(SummonerViewModel::class.java)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
            viewModel = DataObject.viewModel
        }
    }

    private fun observerSetting() {
        //editText observable
        val editTextSubscription =
            binding.searchSummonerEditText.textChanges().debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onNext = {
                        loadSummoner(binding.searchSummonerEditText.text.toString())
                    }
                )

        compositeDisposable.add(editTextSubscription)
    }

    private fun retrofitSetting() {
        var httpClient = OkHttpClient().newBuilder()
            .addInterceptor(Interceptor { chain ->
                var request = chain.request().newBuilder()
                    .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36"
                    )
                    .addHeader("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                    .addHeader("Origin", "https://developer.riotgames.com")
                    .addHeader("X-Riot-Token", API_KEY)
                    .build()
                chain.proceed(request)
            })

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java)
    }

    private fun realmSetting() {
        realm = Realm.getDefaultInstance()
    }

    private fun loadSummoner(summonerName: String) {
        retrofitService.getSummoner(summonerName).enqueue(object : Callback<SummonerData> {
            override fun onResponse(call: Call<SummonerData>, response: Response<SummonerData>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    liveSummonerData.value = SummonerData(
                        body?.id,
                        body?.accountId,
                        body?.puuid,
                        body?.name,
                        body?.profileIconId,
                        body?.revisionDate,
                        body?.summonerLevel
                    )
                } else {
                    liveSummonerData.value = SummonerData()
                }
            }

            override fun onFailure(call: Call<SummonerData>, t: Throwable) {
                liveSummonerData.value = SummonerData()
            }
        })
    }

    private fun selectAllSummoner() {
        var summonerResult = realm.where(SummonerData::class.java).findAll()
        viewModel.selectAllSummoner(summonerResult)
    }
}