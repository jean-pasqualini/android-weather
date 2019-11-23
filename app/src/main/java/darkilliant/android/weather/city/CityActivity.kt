package darkilliant.android.weather.city

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import darkilliant.android.weather.R
import darkilliant.android.weather.utils.launch
import darkilliant.android.weather.utils.toast
import darkilliant.android.weather.weather.WeatherActivity
import darkilliant.android.weather.weather.WeatherFragment

class CityActivity : AppCompatActivity(),
    CityFragment.CityFragmentListener {
    private lateinit var cityFragment: CityFragment
    private var currentCity: City? = null

    private var weatherFragment: WeatherFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)

        cityFragment = supportFragmentManager.findFragmentById(R.id.city_fragment) as CityFragment
        cityFragment.listener = this
        weatherFragment = supportFragmentManager.findFragmentById(R.id.weather_fragment) as WeatherFragment?
    }

    override fun onCitySelected(city: City) {
        this.toast("city selected : ${city.name}")
        currentCity = city

        if (isHandsetLayout()) {
            launch(WeatherActivity::class.java) {
                it.putExtra(WeatherFragment.EXTRA_CITY_NAME, city.name)
            }
        } else {
            weatherFragment?.updateWeatherForCity(city.name)
        }
    }

    override fun onEmptyCity() {
        weatherFragment?.clearUI();
    }

    private fun isHandsetLayout(): Boolean = weatherFragment == null
}
