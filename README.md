## An example of creating a library of line and bar charts to display weather data.

The chart allows you to replace the RecyclerView adapter, which greatly simplifies the project.

The chart is configured visually during layout.

The weather is determined by the specified coordinates; no location determination is required.

Project created in Android Studio Meerkat | 2024.3.1 Patch 2.

![Custom_Chart](https://github.com/user-attachments/assets/743f7b5a-e1d3-45e9-97d4-27f2e7ca1561)

To use the ready-made library, add the dependency:
```
dependencies {

    implementation("io.github.uratera:chart:1.0.2")
}
```
For horizontal scrolling, use HorizontalScrollView.
```
<HorizontalScrollView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:scrollbars="none">

    <com.tera.linechart.LineChart
        android:id="@+id/lineChart"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>

</HorizontalScrollView>
```

### General attributes
|Attributes	|Description	|Default value
|-------------------------|------------------------|--------------|
chart_arrayColor	|Array of colors from resources	|null
chart_axisColor	|Axis color	|light blue
chart_axisShow	|Show axis	|true
chart_axisWidth	|Axis width	|2dp
chart_chartColor	|Chart color	|light blue
chart_itemLength	|Item length	|45dp
chart_iconShow	|Show icons	|true
chart_iconSize	|Icon size	|36dp
chart_iconTop	|Icons on top	|true
chart_labelColor	|Label text color	|black
chart_labelSize	|Label text size	|12sp
chart_labelText	|Label text	|null
chart_markZeroAllHeight	|Full height zero label	|false
chart_markZeroColor	|Zero mark color	|light blue
chart_markZeroShow	|Show zero mark	|false
chart_markZeroWidth	|Zero mark width	|2dp
chart_minHeight	|Minimum chart height	|50dp
chart_Show	|Show chart	|true
chart_Style	|Chart style (line, bar)	|line
chart_textAxisColor	|Axis text color	|black
chart_textAxisShow	|Show axis text	|true
chart_textAxisSize	|Axis text size	|12sp
chart_textAxisTop	|Axis text top	|false
chart_textColor	|Value text color	|black
chart_textFormat	|Text format (string, integer)	|string
chart_textOnLine	|Value text on straight line	   |true
chart_textSize	|Value text size	|12sp
chart_textShow	|Show value text	|true 

### Line chart attributes
|Attributes	|Description	|Default value
|------------------------|------------------------|--------------|
line_fillBottomColor	|Fill color bottom	|dark gray
line_fillTopColor	|Fill color top	|orange
line_fillShow	|Show fill	|false
line_fillStyle	|Fill style (uniform, gradient)	|uniform
line_pointColor	|Points color	|light blue
line_pointRadius	|Points radius	|5dp
line_pointShow	|Show points	|true

### Bar chart attributes
|Attributes	|Description	|Default value
|-----------------------|------------------------|-------------|
bar_gradientShow	|Show gradient	|false
bar_gradientTopColor	|Gradient color top	|orange
bar_partWidth	|Band width in percent	|85

Methods:
```
setDataValueString: ArrayList<String>?
setDataAxisString: ArrayList<String>?
setIcons: ArrayList<Int>?
setColors: ArrayList<Int>?
getChartHeight: Float
```
Without using the setDataValueString method, the chart displays nothing.

