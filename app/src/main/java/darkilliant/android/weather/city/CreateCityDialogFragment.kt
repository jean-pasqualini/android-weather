package darkilliant.android.weather.city

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import darkilliant.android.weather.R

class CreateCityDialogFragment: DialogFragment() {

    interface CreateCityDialogListener {
        fun onDialogPositiveCLick(cityNam: String)
        fun onDialogNegativeClick()
    }

    var listener: CreateCityDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(context)

        val input = EditText(context)

        with(input) {
            inputType = InputType.TYPE_CLASS_TEXT
            hint = context.getString(R.string.create_city_city_hint)
        }

        builder.setTitle(getString(R.string.create_city_title))
            .setView(input)
            .setPositiveButton(getString(R.string.create_city_positive), DialogInterface.OnClickListener { _, _ ->
                listener?.onDialogPositiveCLick(input.text.toString())
            })
            .setNegativeButton(getString(R.string.create_city_negative), DialogInterface.OnClickListener { dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegativeClick()
            })

        return builder.create()
    }
}