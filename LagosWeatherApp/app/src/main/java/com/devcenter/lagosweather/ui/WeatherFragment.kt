package com.devcenter.lagosweather.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.devcenter.lagosweather.R
import com.devcenter.lagosweather.constants.SecretKeys
import com.devcenter.lagosweather.model.WeatherModel
import com.devcenter.lagosweather.retrofit.APIService
import com.devcenter.lagosweather.retrofit.RetrofitClient
import com.devcenter.lagosweather.utils.UiUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.SimpleDateFormat
import java.util.*


class WeatherFragment : Fragment() {

    lateinit var date: TextView
    lateinit var image: ImageView
    lateinit var temperature: TextView
    lateinit var unitTV: TextView
    lateinit var tempDescription: TextView
    lateinit var time: TextView
    lateinit var celciusButton: Button
    lateinit var fahrenheitButton: Button
    lateinit var kelvinButton: Button
    lateinit var mphButton: Button
    lateinit var msButton: Button
    lateinit var temperatureUnitGroup: RadioGroup
    lateinit var speedUnitGroup: RadioGroup
    lateinit var windSpeed: TextView
    private lateinit var rootView: View


    lateinit var apiService: APIService
    lateinit var compositeDisposable: CompositeDisposable

    companion object {
        var sunriseVal: String = "06:41"
        var sunsetVal: String = "18:28"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_weather, container, false)
        initView()
        initAPI()
        fetchData()
        listenToUnitChange()

        return rootView
    }


    private fun initView() {
        date = rootView.findViewById(R.id.tv_date)
        tempDescription = rootView.findViewById(R.id.tv_decription)
        windSpeed = rootView.findViewById(R.id.tv_wind)
        temperature = rootView.findViewById(R.id.tv_temperature)
        image = rootView.findViewById(R.id.img_weather)
        time = rootView.findViewById(R.id.tv_time)
        celciusButton = rootView.findViewById(R.id.rb_celcius)
        kelvinButton = rootView.findViewById(R.id.rb_kelvin)
        fahrenheitButton = rootView.findViewById(R.id.rb_fahrenheit)
        mphButton = rootView.findViewById(R.id.rb_mph)
        msButton = rootView.findViewById(R.id.rb_ms)
        temperatureUnitGroup = rootView.findViewById(R.id.rg_temperature)
        speedUnitGroup = rootView.findViewById(R.id.rg_wind_speed)
        unitTV = rootView.findViewById(R.id.tv_unit)
    }

    private fun initAPI() {
        UiUtils.showProgressDialog(activity, "Fetching data...")
        val retrofit = RetrofitClient.getClient()
        apiService = retrofit.create(APIService::class.java)
        compositeDisposable = CompositeDisposable()
    }

    private fun fetchData() {
        compositeDisposable.add(apiService.retrieveWeather(
            SecretKeys.id,
            SecretKeys.apiKey
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe { weatherInfo -> displayData(weatherInfo) })
    }

    private fun displayData(weathers: WeatherModel) {

        tempDescription.text = weathers.weather[0].description.substring(0, 1).toUpperCase() +
                weathers.weather[0].description.substring(1)
        windSpeed.text = "Wind Speed: " + weathers.wind.speed.toString() + " m/s"
        temperature.text = getTemperatureInCelcius(weathers.main.temp).toString()
        date.text = currentDate()
        time.text = getCurrentTime()

        //Glide.with(activity!!).load(prepareIcon(weathers.weather[0].icon)).into(image)

        sunriseVal = unixToTime(weathers.sys.sunrise)
        sunsetVal = unixToTime(weathers.sys.sunset)

        sendSunInfoToSunFragment(sunriseVal, sunsetVal)

        UiUtils.dismissAllProgressDialogs()

        temperatureUnitGroup.setOnCheckedChangeListener { group, _ ->
            // checkedId is the RadioButton selected
            if(group.checkedRadioButtonId != -1){
                if(rb_celcius.isChecked){
                    temperature.text = getTemperatureInCelcius(weathers.main.temp).toString()
                    unitTV.text="oC"
                }
                if(rb_fahrenheit.isChecked){
                    temperature.text =getTemperatureInFahrenheit(weathers.main.temp).toString()
                    unitTV.text="oF"

                }
                if(rb_kelvin.isChecked){
                    temperature.text = weathers.main.temp.toString()
                    unitTV.text="oK"

                }
            }
        }

        speedUnitGroup.setOnCheckedChangeListener { group, _ ->
            // checkedId is the RadioButton selected
            if(group.checkedRadioButtonId != -1){
                if(rb_mph.isChecked){
                    windSpeed.text = "Wind Speed: " + getSpeedInmph(weathers.wind.speed).toString() + " mph"
                }
                if(rb_ms.isChecked){
                    windSpeed.text = "Wind Speed: " + weathers.wind.speed.toString() + " m/s"
                }
            }
        }
    }

    private fun sendSunInfoToSunFragment(sunriseVal: String, sunsetVal: String) {
        val b = Bundle()
        b.putString("rise", sunriseVal)
        b.putString("set", sunsetVal)

        SunFragment().arguments = b
    }

    private fun unixToTime(unixTimeStamp: Int): String {
        val formatDate = SimpleDateFormat("HH:mm", Locale.ENGLISH)
        val currentTime = Date()
        currentTime.time = unixTimeStamp.toLong() * 1000L
        return formatDate.format(currentTime)
    }

    private fun currentDate(): String {
        val formatDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
        return formatDate.format(Date())
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm")
        return timeFormat.format(calendar.time)
    }

    private fun getTemperatureInCelcius(temp: Double): Double {
        return (temp - 273.15)
    }

    private fun getTemperatureInFahrenheit(temp: Double): Double {
        return (temp - 457.87)
    }

    private fun getSpeedInmph(speed: Double): Double {
        return (speed * 2.237)
    }

    private fun prepareIcon(icon: String): String {
        return "http://openweathermap.org/img/w/$icon.png"
    }

    private fun listenToUnitChange() {
        temperatureUnitGroup.setOnCheckedChangeListener { group, checkedId ->
            // checkedId is the RadioButton selected
//            if(group.checkedRadioButtonId != -1){
//                if(rb_celcius.isChecked){
//
//                }
//                if(rb_fahrenheit.isChecked){
//
//                }
//                if(rb_kelvin.isChecked){
//
//                }
//            }
        }
    }


}
