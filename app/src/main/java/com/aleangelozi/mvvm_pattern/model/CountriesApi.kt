package com.aleangelozi.mvvm_pattern.model

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("all")
    fun getCountries(): Single<List<Country>>
}