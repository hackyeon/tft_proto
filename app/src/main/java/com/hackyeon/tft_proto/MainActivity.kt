package com.hackyeon.tft_proto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hackyeon.tft_proto.data.DataObject.API_KEY
import com.hackyeon.tft_proto.data.DataObject.BASE_URL
import com.hackyeon.tft_proto.data.SummonerData
import com.hackyeon.tft_proto.databinding.ActivityMainBinding
import com.hackyeon.tft_proto.service.RetrofitService
import io.realm.Realm
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var retrofit: Retrofit
    private lateinit var retrofitService: RetrofitService
    private lateinit var realm: Realm
    private lateinit var binding: ActivityMainBinding // 임시 바인딩

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        clickedButton()
    }

    private fun initView(){
        // 통신 설정
        var httpClient = OkHttpClient().newBuilder()
            .addInterceptor (Interceptor { chain ->
                var request = chain.request().newBuilder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36")
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

        // DB 설정
        realm = Realm.getDefaultInstance()
    }


    // 임시 기능
    private fun clickedButton(){
        binding.searchButton.setOnClickListener {
            loadSummoner(binding.searchSummonerEditText.text.toString())
        }
    }

    private fun loadSummoner(summonerName: String){
        retrofitService.getSummoner("").enqueue(object: Callback<SummonerData>{
            override fun onResponse(call: Call<SummonerData>, response: Response<SummonerData>) {
                if(response.isSuccessful){
                    //todo 통신 성공 기능

                    // 임시기능
                    var body = response.body()
                    binding.textView.text = body?.id

                }
            }

            override fun onFailure(call: Call<SummonerData>, t: Throwable) {
                //todo 통신 실패 알리미
            }
        })



    }





}