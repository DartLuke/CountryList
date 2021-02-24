package com.countrylist.ui.bordercountriesactivity

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.countrylist.api.RetrofitBuilder
import com.countrylist.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BorderCountriesViewModel: ViewModel()
{
    private val TAG = "BorderCountriesViewModel"
    val isDescentbyCountry = MutableLiveData(false)
    val isDescentbyArea = MutableLiveData(false)
    val countries = MutableLiveData<List<Country>>()
    val isLoading = MutableLiveData<Int>()
    val errorMessage = MutableLiveData<String>()
    val countryName=MutableLiveData<String>()

    fun sortByCountry() {
        isDescentbyCountry.value = !isDescentbyCountry.value!!
    }

    fun sortByArea() {
        isDescentbyArea.value = !isDescentbyArea.value!!
    }

    fun getBorderCountries(borderCountries: String, countryName_:String){
        isLoading.value = View.VISIBLE
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = RetrofitBuilder.api.getCountriesByCode(borderCountries)
                countries.postValue(result)
                countryName.postValue(countryName_)
           } catch (exp: Exception) {
                errorMessage.value = "Error: $errorMessage"
                Log.e(TAG, exp.message)
            } finally {
                isLoading.postValue(View.GONE)
            }
        }
    }
}