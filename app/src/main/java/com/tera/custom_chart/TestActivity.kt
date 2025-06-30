package com.tera.custom_chart

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tera.custom_chart.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    private val arrayTemp = arrayListOf(
        "20°C", "20°C", "19°C", "18°C", "18°C", "19°C", "20°C", "19°C",
        "20°C", "20°C", "19°C", "19°C", "17°C", "16°C", "16°C", "19°C")

    private val array0 = arrayListOf("0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0")


    private val arrayTime = arrayListOf(
        "20:00", "21:00", "22:00", "23:00", "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00")

    private val arrayColor = arrayListOf(Color.RED, Color.LTGRAY, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN )

    private var arrayIcons = arrayListOf(
        R.drawable.day_1, R.drawable.day_1, R.drawable.day_3, R.drawable.day_82, R.drawable.day_95,
        R.drawable.night_0, R.drawable.night_1, R.drawable.night_3, R.drawable.night_82, R.drawable.night_95
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initChart()
    }

    private fun initChart() = with(binding) {
        val arrayData = arrayTemp
//        val arrayData = array0
        val arrayTime = arrayTime
        val arrayIcon = arrayIcons

        chart.dataValueString = arrayData
        chart.dataAxisString = arrayTime
        chart.icons = arrayIcon
    }
}