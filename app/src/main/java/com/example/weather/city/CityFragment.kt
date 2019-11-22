package com.example.weather.city

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weather.App
import com.example.weather.Database
import com.example.weather.R

class CityFragment: Fragment() {

    private lateinit var database: Database
    private lateinit var citites = MutableList<City>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        database = App.database
        citites = mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, meny)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_create_city -> {
                showCreateCityDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener {
            override fun onDialogNegativeClick() {

            }

            override fun onDialogPositiveCLick(cityNam: String) {
                saveCity(City(cityNam))
            }àà
        }

        createCityFragment.show(fragmentManager!!, "CreateCityDialogFragment")
    }

    private fun saveCity(city: City) {
        if (database.createCity(city)) {
            citites.add(city)
        } else {
            Toast.makeText(context, "Could not create city", Toast.LENGTH_SHORT).show()
        }
    }
}