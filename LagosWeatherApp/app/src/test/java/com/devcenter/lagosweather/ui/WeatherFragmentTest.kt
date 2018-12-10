package com.devcenter.lagosweather.ui

import com.devcenter.lagosweather.utils.ConverterUtil
import org.junit.Assert.*
import org.junit.Test

class WeatherFragmentTest{

    @Test
    fun testGetTemperatureInCelcius(){
        var actual = ConverterUtil.getTemperatureInCelcius(0.0)
        var expected = -273.15

        assertEquals("Kelvin to Celcius conversion failed", expected, actual, 0.001)
    }
    @Test
    fun testGetTemperatureInFahrenheit(){
        var actual = ConverterUtil.getTemperatureInFahrenheit(0.0)
        var expected = -459.67

        assertEquals("Kelvin to Celcius conversion failed", expected, actual, 0.001)
    }
    @Test
    fun testGetSpeedInmph(){
        var actual = ConverterUtil.getSpeedInmph(1.0)
        var expected = 2.237

        assertEquals("Kelvin to Celcius conversion failed", expected, actual, 0.001)
    }

}