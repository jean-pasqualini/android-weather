package darkilliant.android.weather.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import darkilliant.android.weather.App
import darkilliant.android.weather.R
import darkilliant.android.weather.openweathermap.WeatherWrapper
import darkilliant.android.weather.openweathermap.mapOpenWeatherDataToWeather
import darkilliant.android.weather.utils.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherFragment: Fragment() {
    companion object {
        val EXTRA_CITY_NAME = "darkilliant.android.weather.extras.EXTRA_CITY_NAME"

        fun newInstance() = WeatherFragment()
    }

    private val TAG = WeatherFragment::class.java.simpleName

    private lateinit var cityName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, null)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setOnRefreshListener { this.refreshWeather() }

        if (activity?.intent!!.hasExtra(EXTRA_CITY_NAME)) {
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME) as String)
        } else {
            Log.e(TAG, "not extra")
        }
    }

    fun updateWeatherForCity(cityName: String) {
        this.cityName = cityName

        this.city.text = cityName

        if (!swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = true
        }

        val call = App.weatherService.getWeather("$cityName,fr")
        call.enqueue(object: Callback <WeatherWrapper> {
            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, "Could not load city weather", t)
                activity?.toast(getString(R.string.weather_error_could_not_load_information_meteo))

                swipe_refresh.isRefreshing = false
            }

            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                Log.i(TAG, "OpenWeatherMap response: ${response.body()}")

                response.body()?.let {
                    val weather =
                        mapOpenWeatherDataToWeather(it)
                    Log.i(TAG, "Weather response: ${weather}")
                    updateUi(weather)
                }

                swipe_refresh.isRefreshing = false
            }
        });
    }

    private fun updateUi(weather: Weather) {

        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_cloud_off_black_24dp)
            .into(weather_icon)

        weather_description.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }

    private fun refreshWeather() {
        updateWeatherForCity(this.cityName)
    }

    fun clearUI() {
        weather_icon.setImageResource(R.drawable.ic_cloud_off_black_24dp)
        cityName = ""
        city.text = ""
        weather_description.text = ""
        temperature.text = ""
        humidity.text = ""
        pressure.text = ""
    }

}