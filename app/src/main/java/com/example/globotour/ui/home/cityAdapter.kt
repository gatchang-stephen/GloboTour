package com.example.globotour.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.R

class CityAdapter(var context: Context, private var cityList: ArrayList<City>) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    private val TAG = "cityAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        Log.i(TAG, "onCreateViewHolder: created")
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder: position $position")
        val city = cityList[position]
        holder.setData(city, position)
        holder.setListener()
    }

    override fun getItemCount(): Int = cityList.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var currentPosition: Int = -1
        private var currentCity: City? = null
        private var txvCityName = itemView.findViewById<TextView>(R.id.txv_city_name)
        private var imvCityImage = itemView.findViewById<ImageView>(R.id.imv_city)
        private var imvDelete = itemView.findViewById<ImageView>(R.id.imv_delete)
        private var imvFavorite = itemView.findViewById<ImageView>(R.id.imv_favorite)
        private val icFavoriteFilledImage =
            AppCompatResources.getDrawable(context, R.drawable.ic_favorite_filled)
        private val icFavoriteBorderedImage =
            AppCompatResources.getDrawable(context, R.drawable.ic_favorite_bordered)

        fun setData(city: City, position: Int) {
            txvCityName.text = city.name
            imvCityImage.setImageResource(city.imageId)

            if (city.isFavorite) {
                imvFavorite.setImageDrawable(icFavoriteFilledImage)
            } else {
                imvFavorite.setImageDrawable(icFavoriteBorderedImage)
            }

            this.currentCity = city
            this.currentPosition = position
        }

        fun setListener() {
            imvFavorite.setOnClickListener(this)
            imvDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.imv_delete -> {
                    cityList.removeAt(currentPosition)
                    notifyItemRemoved(currentPosition)
                    notifyItemRangeChanged(currentPosition, cityList.size)
                    VacationSpots.favoriteCityList.remove(currentCity!!)

                }
                R.id.imv_favorite -> {
                    currentCity?.isFavorite = !currentCity?.isFavorite!!

                    if (currentCity?.isFavorite!!) {
                        imvFavorite.setImageDrawable(icFavoriteFilledImage)
                        VacationSpots.favoriteCityList.add(currentCity!!)
                    } else {
                        imvFavorite.setImageDrawable(icFavoriteBorderedImage)
                        VacationSpots.favoriteCityList.remove(currentCity!!)
                    }
                }

            }
        }
    }
}