package com.countrylist.api


import com.countrylist.Api.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://restcountries.eu/"
    private fun retrofit() =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

    val api: Api = retrofit().create(Api::class.java)
}