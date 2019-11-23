package com.example.weather.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R

class CityAdapter(
    private val cities: List<City>,
    private val cityListener: CityAdapter.CityItemListener
) : RecyclerView.Adapter<CityAdapter.ViewHolder>(), View.OnClickListener {

    interface CityItemListener {
        fun onCitySelected(city: City)
        fun onCityDeleted(city: City)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView  = itemView.findViewById<CardView>(R.id.card_view)!!
        var cityNameView = itemView.findViewById<TextView>(R.id.name)!!
        var deleteView = itemView.findViewById<View>(R.id.delete)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)

        return ViewHolder(viewItem)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var city = cities[position]

        with(holder) {
            cardView.tag = city
            cityNameView.text = city.name
            cardView.setOnClickListener(this@CityAdapter)
            deleteView.tag = city
            deleteView.setOnClickListener(this@CityAdapter)
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.card_view -> cityListener.onCitySelected(view.tag as City)
            R.id.delete -> cityListener.onCityDeleted(view.tag as City)
        }
    }



}