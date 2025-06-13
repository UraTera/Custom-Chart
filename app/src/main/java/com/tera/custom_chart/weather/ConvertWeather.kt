package com.tera.custom_chart.weather

import android.content.Context
import com.tera.custom_chart.R
import com.tera.custom_chart.utils.MyConst
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ConvertWeather(private val context: Context) {

    fun getTemperature(temp: Double, numTemp: Int ): String {
        val res = if (numTemp == 0)
            temp.roundToInt().toString() + "°C"
        else
            (temp * 1.8 + 32.0).roundToInt().toString() + "°F"
        return res
    }

    // Давление
    fun getPress(value: Double, numPress: Int): String {
        var res = ""
        when(numPress) {
            0 -> {
                val press = value.roundToInt()
                val unit = context.getString(R.string.unit_press_hpa)
                res = "$press $unit"
            }
            1 -> {
                val press = value.roundToInt()
                val unit = context.getString(R.string.unit_press_mbr)
                res = "$$press $unit"
            }
            2 -> {
                val press = (value * MyConst.HPA_MM_HG).roundToInt()
                val unit = context.getString(R.string.unit_press_mmHg)
                res = "$press $unit"
            }
        }
        return res
    }

    // Скорость ветра
    fun getWind(value: Double, numWind: Int): String{
        var res = ""
        when(numWind) {
            0 -> {
                val speed = value.roundToInt()
                val unit = context.getString(R.string.unit_wind_kmh)
                res = "$speed $unit"
            }
            1 -> {
                val speed = (value / 3.6).roundToInt()
                val unit = context.getString(R.string.unit_wind_mc)
                res = "$speed $unit"
            }
            2 -> {
                val speed = (value * MyConst.KM_MIL).roundToInt()
                val unit = context.getString(R.string.unit_wind_mlh)
                res = "$speed $unit,"
            }
        }
        return res
    }

    // Текущее время
    fun getCurrentTime(pattern: String): String {
        val stf = SimpleDateFormat(pattern, Locale.getDefault())
        return stf.format(Date()).toString()
    }

     // Формат Даты и времени
    fun formatDate(date: String, format: String, field: String) : String{
        val dayOfWeek = getFromDateTime(date, format, field)
        return dayOfWeek.toString()
    }

    private fun getFromDateTime(dateTime: String, dateFormat: String, field: String): String? {
        val input = SimpleDateFormat(dateFormat, Locale.getDefault())
        val output = SimpleDateFormat(field, Locale.getDefault())
        try {
            val getAbbreviate = input.parse(dateTime)
            return getAbbreviate?.let { output.format(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }


    fun getIcon(isDay: Int, code: Int): Int {
        return if (isDay == 1) getIconDay(code)
        else getIconNight(code)
    }

    // Иконки День
    private fun getIconDay(code: Int): Int {
        val rs = when (code) {
            0 -> R.drawable.day_0     // Ясно
            1, 2 -> R.drawable.day_1  // Малооблачно, Переменная облачность
            3 -> R.drawable.day_3     // Пасмурно
            45 -> R.drawable.day_45   // Туман
            48 -> R.drawable.day_48   // Туман
            51, 53, 55, 61, 63 -> R.drawable.day_51 // Моросящий дождь
            56, 57, 66 -> R.drawable.day_56 // Замерзающая морос
            65 -> R.drawable.day_65  // Сильный дождь
            67 -> R.drawable.day_67  // Сильный ледяной дождь
            71, 73, 85 -> R.drawable.day_71 // Слабый снег, Умеренный снег
            75, 86 -> R.drawable.day_75 // Сильный снегопад
            77 -> R.drawable.day_77 // Снежная крупа
            80 -> R.drawable.day_80 // Небольшие ливни
            81 -> R.drawable.day_81 // Умеренные ливни
            82 -> R.drawable.day_82 // Сильные ливни
            95 -> R.drawable.day_95 // Гроза
            96, 99 -> R.drawable.day_96 // Гроза с градом
            else -> R.drawable.unknown
        }
        return rs
    }

    // Иконки Ночь
    private fun getIconNight(code: Int): Int {
        val rs = when (code) {
            0 -> R.drawable.night_0    // Ясно
            1, 2 -> R.drawable.night_1 // Малооблачно, Переменная облачность
            3 -> R.drawable.night_3    // Пасмурно
            45 -> R.drawable.night_45  // Туман
            48 -> R.drawable.night_48  // Туман
            51, 53, 55, 61, 63 -> R.drawable.night_51 // Моросящий дождь
            56, 57, 66 -> R.drawable.night_56 // Замерзающая морос
            65 -> R.drawable.night_65 // Сильный дождь
            67 -> R.drawable.night_67 // Сильный ледяной дождь
            71, 73, 85 -> R.drawable.night_71 // Слабый снег, Умеренный снег
            75, 86 -> R.drawable.night_75 // Сильный снегопад
            77 -> R.drawable.night_77 // Снежная крупа
            80 -> R.drawable.night_80 // Небольшие ливни
            81 -> R.drawable.night_81 // Умеренные ливни
            82 -> R.drawable.night_82 // Сильные ливни
            95 -> R.drawable.night_95 // Гроза
            96, 99 -> R.drawable.night_96 // Гроза с градом
            else -> R.drawable.unknown
        }
        return rs
    }

}