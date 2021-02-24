package com.countrylist.Api

import com.countrylist.model.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(value = "rest/v2/all")
    suspend fun getAllCountries(): List<Country>

    @GET(value = " /rest/v2/alpha")
    suspend fun getCountriesByCode(@Query("codes") codes: String): List<Country>

}
