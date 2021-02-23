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

    val countries = MutableLiveData<List<Country>>()
    val isLoading = MutableLiveData<Int>()

    fun getAllCountries() {
        isLoading.value = View.VISIBLE
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val result = RetrofitBuilder.api.getAllCountries()
                countries.postValue(result)
            } catch (exp: Exception) {
                Log.e("Test", exp.message)
            } finally {
                isLoading.postValue(View.GONE)
            }
        }
    }
}