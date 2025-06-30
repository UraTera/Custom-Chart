package com.tera.custom_chart.weather

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.tera.custom_chart.MainActivity
import com.tera.custom_chart.utils.MyConst
import org.json.JSONObject

class Weather(private val context: Context) {

    companion object {
        const val FORMAT_DATE = "yyyy-MM-dd HH:mm"
        const val URL_API = "https://api.open-meteo.com/v1/forecast?"
    }

    private val sp = context.getSharedPreferences(MyConst.SETTING, Context.MODE_PRIVATE)
    private var activity = MainActivity()
    private var latitude = 25.7616798
    private var longitude = -80.1917902
    private var timeZone = "America/New_York"
    private var numTemp = 0
    private var numPress = 0
    private var numWind = 0

    private fun getParams() {
        numTemp = sp.getInt(MyConst.NUM_TEMP, 0)
        numPress = sp.getInt(MyConst.NUM_PRESS, 0)
        numWind = sp.getInt(MyConst.NUM_WIND, 0)
    }

    fun getWeather() {

        getParams()

        val url = URL_API +
                "latitude=$latitude&" +
                "longitude=$longitude&" +
                "timezone=$timeZone&" +
                "hourly=temperature_2m,surface_pressure,wind_speed_10m,weather_code,is_day&"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.d("myLogs", "response: $response")
                val obj = JSONObject(response)
                hoursWeather(obj)

            },
            {
                Log.d("myLogs", "Error: $it")
            })
        queue.add(request)

    }

    private fun hoursWeather(obj: JSONObject) {

        val arrayTemp = ArrayList<String>()
        val arrayTime = ArrayList<String>()
        val arrayPress = ArrayList<String>()
        val arrayWind = ArrayList<String>()
        val arrayIcon = ArrayList<Int>()
        var indexZero = 0
        var dayWeek = ""

        val convert = ConvertWeather(context)
        val hours = obj.getJSONObject("hourly")
        val timeArray = hours.getJSONArray("time")
        val tempsArray = hours.getJSONArray("temperature_2m")
        val pressArray = hours.getJSONArray("surface_pressure")
        val windArray = hours.getJSONArray("wind_speed_10m")
        val codeArray = hours.getJSONArray("weather_code")
        val isDayArray = hours.getJSONArray("is_day")

        // Получить текущий час
        val hour = convert.getCurrentTime("H").toInt() + 1

        for ((index, i) in (hour..hour + 23).withIndex()) {
            val dateTime = timeArray[i].toString().replace('T', ' ')
            val time = convert.formatDate(dateTime, FORMAT_DATE, "H:mm")

            val tempC = tempsArray[i].toString().toDouble()
            val temp = convert.getTemperature(tempC, numTemp)

            val pressureDb = pressArray[i].toString().toDouble()
            val press = convert.getPress(pressureDb, numPress)

            val windDb = windArray[i].toString().toDouble()
            val wind = convert.getWind(windDb, numWind)

            val icon = convert.getIcon(isDayArray[i].hashCode(), codeArray[i].hashCode())

            val timeInt = time.filter { it.isDigit() }.toInt()
            if (timeInt == 0) {
                indexZero = index
                dayWeek = convert.formatDate(dateTime, FORMAT_DATE, "EEE.")
                dayWeek = dayWeek.replaceFirstChar { it.uppercase() }
            }

            arrayTime.add(time)
            arrayTemp.add(temp)
            arrayPress.add(press)
            arrayWind.add(wind)
            arrayIcon.add(icon)
        }

        val time = arrayTime[indexZero]
        arrayTime[indexZero] = "$dayWeek $time"

        activity.setChart(context, arrayTime, arrayTemp, arrayPress, arrayWind, arrayIcon, false)

    }


}