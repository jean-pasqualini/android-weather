package com.example.weather.city

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.App
import com.example.weather.Database
import com.example.weather.R
import com.example.weather.utils.toast

class CityFragment: Fragment(), CityAdapter.CityItemListener {

    var listener: CityFragmentListener? = null

    private lateinit var database: Database
    private lateinit var citites: MutableList<City>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter

    interface CityFragmentListener {
        fun onCitySelected(city: City)
        fun onEmptyCity()
    }

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
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        citites = database.getAllCitites()

        adapter = CityAdapter(citites, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_city, menu)
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

    override fun onCityDeleted(city: City) {
        showDeleteCityDialog(city)
    }

    override fun onCitySelected(city: City) {
        listener?.onCitySelected(city)
    }

    fun selectFirstCity() {
        when (citites.isEmpty()) {
            true -> listener?.onEmptyCity()
            false -> listener?.onCitySelected(citites.first())
        }
    }

    private fun showCreateCityDialog() {
        val createCityFragment = CreateCityDialogFragment()
        createCityFragment.listener = object: CreateCityDialogFragment.CreateCityDialogListener {
            override fun onDialogNegativeClick() {

            }

            override fun onDialogPositiveCLick(cityNam: String) {
                saveCity(City(cityNam))
            }
        }

        createCityFragment.show(fragmentManager!!, "CreateCityDialogFragment")
    }


    private fun showDeleteCityDialog(city: City) {
        val deleteCityFragment = DeleteCityDialogFragment.newInstance(city.name)

        deleteCityFragment.listener = object: DeleteCityDialogFragment.DeleteCityDialogListener {
            override fun onDialogNegativeClick() { }

            override fun onDialogPositiveClick() {
                deleteCity(city)
            }
        }

        deleteCityFragment.show(fragmentManager!!, "DeleteCityDialogFragment")
    }

    private fun deleteCity(city: City) {
        if (database.deleteCity(city)) {
            citites.remove(city)
            adapter.notifyDataSetChanged()
            this.selectFirstCity()
            context!!.toast(getString(R.string.city_message_info_deleted, city.name))
        } else {
            context!!.toast(getString(R.string.city_message_error_count_not_create_city))
        }
    }

    private fun saveCity(city: City) {
        if (database.createCity(city)) {
            citites.add(city)
            adapter.notifyDataSetChanged()
        } else {
            context!!.toast(getString(R.string.city_message_error_count_not_delete_city))
        }
    }
}