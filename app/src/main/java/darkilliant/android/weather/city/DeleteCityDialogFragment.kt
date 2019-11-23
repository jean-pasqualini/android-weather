package darkilliant.android.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import darkilliant.android.weather.R

class DeleteCityDialogFragment: DialogFragment() {

    interface DeleteCityDialogListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    companion object {
        val EXTRA_CITY_NAME = "darkilliant.android.weather.extras.EXTRA_CITY_NAME"

        fun newInstance(cityName: String): DeleteCityDialogFragment {
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_NAME, cityName)
            }

            return fragment
        }
    }

    var listener: DeleteCityDialogListener? = null

    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cityName = arguments!!.getString(EXTRA_CITY_NAME).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        builder.setTitle(getString(R.string.delete_city_title, cityName))

        builder.setPositiveButton(getString(R.string.delete_city_positive))
            { _, _ -> listener?.onDialogPositiveClick() }

        builder.setNegativeButton(getString(R.string.delete_city_negative))
            { _, _ -> listener?.onDialogNegativeClick() }

        return builder.create()
    }
}