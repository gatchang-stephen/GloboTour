package com.example.globotour.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.globotour.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeRecyclerView: RecyclerView
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        //        Adapter
        val cityAdapter = CityAdapter(context, VacationSpots.cityList!!)
        //        RecyclerView
        homeRecyclerView = binding.homeRecyclerview
        homeRecyclerView.setHasFixedSize(true)
        homeRecyclerView.adapter = cityAdapter
        homeRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        homeViewModel.text.observe(viewLifecycleOwner, {
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}