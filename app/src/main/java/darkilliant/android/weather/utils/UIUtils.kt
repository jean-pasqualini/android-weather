package darkilliant.android.weather.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun <T> AppCompatActivity.launch(cls: Class<T>, extraHandler: ((Intent) -> Unit)?) {
    var intent = Intent(this, cls)
    if (extraHandler != null) {
        extraHandler(intent)
    }
    startActivity(intent)
}