package com.example.globotour.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.globotour.databinding.FragmentFavoriteBinding
import com.example.globotour.ui.home.City
import com.example.globotour.ui.home.VacationSpots
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteCityList: ArrayList<City>
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favRecyclerView: RecyclerView
    private var _binding: FragmentFavoriteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteViewModel =
            ViewModelProvider(this).get(FavoriteViewModel::class.java)

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context = requireContext()

        favoriteCityList = VacationSpots.favoriteCityList as ArrayList<City>
        //        Adapter
        favoriteAdapter = FavoriteAdapter(favoriteCityList)
        //        RecyclerView
        favRecyclerView = binding.favoriteRecyclerview
        favRecyclerView.setHasFixedSize(true)
        favRecyclerView.adapter = favoriteAdapter
        favRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        itemTouchHelper.attachToRecyclerView(favRecyclerView)

        favoriteViewModel.text.observe(viewLifecycleOwner, {
        })
        return root
    }

    private var itemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                // move item in `fromPos` to `toPos` in adapter.
                Collections.swap(favoriteCityList, fromPos, toPos)
                recyclerView.adapter?.notifyItemMoved(fromPos, toPos)
                return true // true if moved, false otherwise
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                // remove from adapter
                val position = viewHolder.adapterPosition
                val deleteCity: City = favoriteCityList[position]

                updateCityList(deleteCity, false)
                deleteItem(position)

                context?.let {
                    Snackbar.make(it, favRecyclerView, "Delete", Snackbar.LENGTH_LONG)
                        .setAction("UNDO") {
                            undoDelete(position, deleteCity)
                            updateCityList(deleteCity, true)
                        }.show()
                }
            }
        })

    private fun undoDelete(position: Int, deleteCity: City) {
        favoriteCityList.add(position, deleteCity)
        favoriteAdapter.notifyItemInserted(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
    }

    private fun updateCityList(deleteCity: City, isFavorite: Boolean) {
        val cityList = VacationSpots.cityList!!
        val position = cityList.indexOf(deleteCity)
        cityList[position].isFavorite = isFavorite
    }

    private fun deleteItem(position: Int) {
        favoriteCityList.removeAt(position)
        favoriteAdapter.notifyItemRemoved(position)
        favoriteAdapter.notifyItemRangeChanged(position, favoriteCityList.size)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}