package com.example.globotour.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.R
import com.example.globotour.ui.home.City

class FavoriteAdapter(private var favCityList: ArrayList<City>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val TAG = "FavoriteAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        Log.i(TAG, "onCreateViewHolder: created")
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_favorite, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder: position $position")
        val city = favCityList[position]
        holder.setData(city, position)
    }

    override fun getItemCount(): Int = favCityList.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentPosition: Int = -1
        private var currentCity: City? = null
        private var txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private var imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)


        fun setData(city: City, position: Int) {
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)

            this.currentCity = city
            this.currentPosition = position
        }
    }
}