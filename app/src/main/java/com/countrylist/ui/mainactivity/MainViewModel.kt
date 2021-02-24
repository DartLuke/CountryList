package com.countrylist.ui.mainactivity

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import com.countrylist.api.RetrofitBuilder
import com.countrylist.model.Country

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {
    private val TAG = "MainViewModel"

    val isDescentbyCountry = MutableLiveData(false)
    val isDescentbyArea = MutableLiveData(false)
    val countries = MutableLiveData<List<Country>>()
    val isLoading = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()
    fun sortByCountry() {
        isDescentbyCountry.value = !isDescentbyCountry.value!!
    }

    fun sortByArea() {
        isDescentbyArea.value = !isDescentbyArea.value!!
    }

    fun getAllCountries() {
        isLoading.value = View.VISIBLE
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = RetrofitBuilder.api.getAllCountries()
                countries.postValue(result)
            } catch (exp: Exception) {
                errorMessage.value = "Error: $errorMessage"
                Log.e(TAG, exp.message)
            } finally {
                isLoading.postValue(View.GONE)
            }
        }
    }
}