package com.tera.custom_chart

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tera.custom_chart.databinding.ActivityMainBinding
import com.tera.custom_chart.utils.MyConst
import com.tera.custom_chart.weather.Weather

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val gson = Gson()
    private var keyUpdate = false
    private var numTemp = 0
    private var numPress = 0
    private var numWind = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initButton()

        Handler(Looper.getMainLooper()).postDelayed({
            setPosText()
        }, 200)

    }

    private fun setPosText() = with(binding){
        val marginTop = resources.getDimension(R.dimen.margin_top)
        val chartHeight = lineChart.chartHeight
        var margin = (marginTop + chartHeight).toInt()

        var param = tvPress.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = margin
        tvPress.layoutParams = param

        margin += chartHeight.toInt()

        param = tvWind.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = margin
        tvWind.layoutParams = param
    }

    override fun onResume() {
        super.onResume()
        restoreDate()
        Weather(this).getWeather()
    }

    // Загрузка диаграммы
    fun setChart(
        context: Context, listTime: ArrayList<String>, listTemp: ArrayList<String>,
        listPress: ArrayList<String>, listWind: ArrayList<String>, listIcon: ArrayList<Int>,
        keyRestore: Boolean
    ) {
        val view = context as Activity
        val chartTemp = view.findViewById<Chart>(R.id.lineChart)
        val chartPress = view.findViewById<Chart>(R.id.barChart)
        val chartWind = view.findViewById<Chart>(R.id.lineChart2)
        val tvTest = view.findViewById<TextView>(R.id.tvTest)

        val temp = listTemp[0]
        tvTest.text = temp

        setText(context)
        chartTemp.dataValueString = listTemp
        chartTemp.dataAxisString = listTime
        chartTemp.icons = listIcon

        chartPress.dataAxisString = listTime
        chartPress.dataValueString = listPress

        chartWind.dataAxisString = listTime
        chartWind.dataValueString = listWind

        if (keyRestore) return

        val timeStr = gson.toJson(listTime)
        val tempStr = gson.toJson(listTemp)
        val pressStr = gson.toJson(listPress)
        val windStr = gson.toJson(listWind)
        val iconStr = gson.toJson(listIcon)

        val sp = context.getSharedPreferences(MyConst.SETTING, Context.MODE_PRIVATE)
        sp.edit {
            putString(MyConst.LIST_TIME, timeStr)
            putString(MyConst.LIST_TEMP, tempStr)
            putString(MyConst.LIST_PRESS, pressStr)
            putString(MyConst.LIST_WIND, windStr)
            putString(MyConst.LIST_ICON, iconStr)
        }
    }

    private fun setText(context: Context) {
        val view = context as Activity
        val tvPress = view.findViewById<TextView>(R.id.tvPress)
        val tvWind = view.findViewById<TextView>(R.id.tvWind)

        val sp = context.getSharedPreferences(MyConst.SETTING, Context.MODE_PRIVATE)
        numPress = sp.getInt(MyConst.NUM_PRESS, 0)
        numWind = sp.getInt(MyConst.NUM_WIND, 0)

        var str1 = context.getString(R.string.press)
        var str2 = ""
        when(numPress){
            0 -> str2 = context.getString(R.string.unit_press_hpa)
            1 -> str2 = context.getString(R.string.unit_press_mbr)
            2 -> str2 = context.getString(R.string.unit_press_mmHg)
        }
        var text = "$str1, $str2"
        tvPress.text = text

        str1 = context.getString(R.string.wind)
        when(numWind){
            0 -> str2 = context.getString(R.string.unit_wind_kmh)
            1 -> str2 = context.getString(R.string.unit_wind_mc)
            2 -> str2 = context.getString(R.string.unit_wind_mlh)
        }
        text = "$str1, $str2"
        tvWind.text = text
    }

    private fun initButton() = with(binding) {
        rgTemp.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbC -> numTemp = 0
                R.id.rbF -> numTemp = 1
            }
            keyUpdate = true
        }
        rgPress.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbHPa -> numPress = 0
                R.id.rbMBar -> numPress = 1
                R.id.rbMmHg -> numPress = 2
            }
            keyUpdate = true
        }
        rgWind.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbKmHour -> numWind = 0
                R.id.rbMSec -> numWind = 1
                R.id.rbMiHour -> numWind = 2
            }
            keyUpdate = true
        }
        bnUpdate.setOnClickListener {
            if (keyUpdate) {
                saveParams()
                Weather(this@MainActivity).getWeather()
            }
            keyUpdate = false
        }
    }

    private fun saveParams(){
        val sp = getSharedPreferences(MyConst.SETTING, Context.MODE_PRIVATE)
        sp.edit() {
            putInt(MyConst.NUM_TEMP, numTemp)
            putInt(MyConst.NUM_PRESS, numPress)
            putInt(MyConst.NUM_WIND, numWind)
        }
    }

    private fun setParams() = with(binding) {
        when (numTemp) {
            0 -> rbC.isChecked = true
            1 -> rbF.isChecked = true
        }
        when (numPress) {
            0 -> rbHPa.isChecked = true
            1 -> rbMBar.isChecked = true
            2 -> rbMmHg.isChecked = true
        }
        when (numWind) {
            0 -> rbKmHour.isChecked = true
            1 -> rbMSec.isChecked = true
            2 -> rbMiHour.isChecked = true
        }
        keyUpdate = false
    }

    private fun restoreDate() {
        val sp = getSharedPreferences(MyConst.SETTING, Context.MODE_PRIVATE)
        numTemp = sp.getInt(MyConst.NUM_TEMP, 0)
        numPress = sp.getInt(MyConst.NUM_PRESS, 0)
        numWind = sp.getInt(MyConst.NUM_WIND, 0)

        setParams()

        val typeStr = object : TypeToken<ArrayList<String>>() {}.type
        val typeInt = object : TypeToken<ArrayList<Int>>() {}.type

        val timeStr = sp.getString(MyConst.LIST_TIME, "")
        val tempStr = sp.getString(MyConst.LIST_TEMP, "")
        val pressStr = sp.getString(MyConst.LIST_PRESS, "")
        val windStr = sp.getString(MyConst.LIST_WIND, "")
        val iconStr = sp.getString(MyConst.LIST_ICON, "")

        val listTime = gson.fromJson<ArrayList<String>>(timeStr, typeStr)
        val listTemp = gson.fromJson<ArrayList<String>>(tempStr, typeStr)
        val listPress = gson.fromJson<ArrayList<String>>(pressStr, typeStr)
        val listWind = gson.fromJson<ArrayList<String>>(windStr, typeStr)
        val listIcon = gson.fromJson<ArrayList<Int>>(iconStr, typeInt)

        if (listTime != null && listTemp != null && listPress != null &&
            listWind != null && listIcon != null)
            setChart(this, listTime, listTemp, listPress, listWind, listIcon, true)

    }


}