package com.devcenter.lagosweather.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devcenter.lagosweather.R
import java.text.SimpleDateFormat
import java.util.*


class SunFragment : Fragment() {
    private lateinit var currentDate: TextView
    private lateinit var currentTime: TextView
    private lateinit var sunrise: TextView
    private lateinit var sunset: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_sun, container, false)
        currentDate = rootView.findViewById(R.id.tv_date_sun)
        currentTime = rootView.findViewById(R.id.tv_time_sun)
        sunrise = rootView.findViewById(R.id.tv_sunrise)
        sunset = rootView.findViewById(R.id.tv_sunset)

        displayData()
        return rootView
    }

    private fun displayData() {
        currentDate.text = currentDate()
        currentTime.text = getCurrentTime()

        val bundle = this.arguments

        if (bundle != null) {
            sunrise.text = bundle.getString("rise")
            sunset.text = bundle.getString("set")
        }

//        sunrise.text = WeatherFragment.sunriseVal
//        sunset.text = WeatherFragment.sunsetVal
    }

    private fun currentDate(): String{
        val formatDate = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
        return formatDate.format(Date())
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val timeFormat = SimpleDateFormat("HH:mm")
        return timeFormat.format(calendar.time)
    }

}
