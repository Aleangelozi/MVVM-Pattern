package com.aleangelozi.mvvm_pattern.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    val countryName: String
)
