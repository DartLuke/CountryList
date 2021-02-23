package com.countrylist.UI.MainActivity

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countrylist.Api.RetrofitBuilder
import com.countrylist.model.Country
import com.countrylist.utils.Utils
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