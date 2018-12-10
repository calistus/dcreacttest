package com.devcenter.lagosweather.utils

object ConverterUtil {
    fun getTemperatureInCelcius(temp: Double): Double {
        return (temp - 273.15)
    }

    fun getTemperatureInFahrenheit(temp: Double): Double {
        return (temp - 459.67)
    }

    fun getSpeedInmph(speed: Double): Double {
        return (speed * 2.237)
    }
}