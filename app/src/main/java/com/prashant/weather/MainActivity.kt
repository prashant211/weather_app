package com.prashant.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    lateinit var city: TextView
    lateinit var Coordinates: TextView
    lateinit var weatheri: TextView
    lateinit var temprature: TextView
    lateinit var mintemprature: TextView
    lateinit var maxtemprature: TextView
    lateinit var pressure: TextView
    lateinit var humidity: TextView
    lateinit var wind: TextView
    lateinit var degree: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Coordinates = findViewById(R.id.coordinates)
        weatheri = findViewById(R.id.weather)
        temprature = findViewById(R.id.temp)
        mintemprature = findViewById(R.id.mintemp)
        maxtemprature = findViewById(R.id.maxtemp)
        city = findViewById(R.id.cityname)
        pressure = findViewById(R.id.pressure)
        humidity = findViewById(R.id.humidity)
        wind = findViewById(R.id.wind)
        degree = findViewById(R.id.degree)


        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")
        getJsonData(lat, long)

    }

    private fun getJsonData(lat: String?, long: String?) {

        val Api = "d782c3641dcc9cbcb8528dfb0245b3e4"
        val queue = Volley.newRequestQueue(this)
        val url =
            "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${Api}"

// Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                setvalues(response)
            },
            Response.ErrorListener { Toast.makeText(this, "error", Toast.LENGTH_LONG).show() })

// Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }

    private fun setvalues(response: JSONObject) {

        city.text = response.getString("name")

        var latitude = response.getJSONObject("coord").getString("lat")
        var longitude = response.getJSONObject("coord").getString("lon")
        Coordinates.text = "${latitude},${longitude}"

        weatheri.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

        var temporarytemp = response.getJSONObject("main").getString("temp")
        temporarytemp = ((((temporarytemp).toFloat() - 273.15)).toInt()).toString()
        temprature.text = "${temporarytemp}°C"

        var mintem = response.getJSONObject("main").getString("temp_min")
        mintem = ((((mintem).toFloat() - 273.15)).toInt()).toString()
        mintemprature.text =mintem+"°C"

        var maxtem = response.getJSONObject("main").getString("temp_min")
        maxtem = ((kotlin.math.ceil((maxtem).toFloat() - 273.15)).toInt()).toString()
        maxtemprature.text =maxtem+"°C"

        pressure.text = response.getJSONObject("main").getString(("pressure"))
        humidity.text = response.getJSONObject("main").getString("humidity") + "%"

        wind.text = response.getJSONObject("wind").getString("speed")
        degree.text = "Degree :" + response.getJSONObject("wind").getString("deg") + "°"

    }
}