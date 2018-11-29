package com.devcenter.lagosweather.retrofit

import com.devcenter.lagosweather.model.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("data/2.5/weather")
    fun retrieveWeather(
        @Query("id") cityId: String,
        @Query("APPID") key: String):  Observable<WeatherModel>

}