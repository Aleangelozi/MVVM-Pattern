package com.aleangelozi.mvvm_pattern.model

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    companion object {
        private const val BASE_URL = "https://restcountries.eu/rest/v2/"
    }

    private var api: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        api = retrofit.create(CountriesApi::class.java)
    }

    fun getCountries(): Single<List<Country>> = api.getCountries()
}