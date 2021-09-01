package com.aleangelozi.mvvm_pattern.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aleangelozi.mvvm_pattern.model.CountriesService
import com.aleangelozi.mvvm_pattern.model.Country
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.ArrayList

class CountriesViewModel : ViewModel() {

    private val countries: MutableLiveData<List<String>> = MutableLiveData()
    private val countryError: MutableLiveData<Boolean> = MutableLiveData()

    private val service: CountriesService = CountriesService()


    init {
        fetchCountries()
    }

    fun getCountries(): LiveData<List<String>> {
        return countries
    }

    fun getCountryError(): LiveData<Boolean> {
        return countryError
    }

    private fun fetchCountries() {
        service.getCountries()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(t: List<Country>?) {
                    val countryNames: MutableList<String> = ArrayList()

                    if (t != null) {
                        for (country in t) {
                            countryNames.add(country.countryName)
                        }
                        countries.value = countryNames
                        countryError.value = false
                    }
                }

                override fun onError(e: Throwable?) {
                    countryError.value = true
                }

            })
    }

    fun onRefresh() = fetchCountries()
}