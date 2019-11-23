package darkilliant.android.weather.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import darkilliant.android.weather.R

class WeatherActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.weather_activity)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,
                WeatherFragment.newInstance()
            )
            .commit()
    }
}