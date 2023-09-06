package com.example.netflix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflix.adapters.MoviesItemAdapter
import com.example.netflix.adapters.TopSearchAdapter
import com.example.netflix.databinding.FragmentSearchBinding

class SearchFragment:ParentFragment() {
    private var _binding: FragmentSearchBinding? = null
    private val moviesAdapter= MoviesItemAdapter()
    private val topSearchAdapter =TopSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return _binding?.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager= GridLayoutManager(requireContext(),3)

        _binding?.movieRecyclerView?.apply {
            setLayoutManager(layoutManager)
            adapter= moviesAdapter
        }
        val layoutManager1= LinearLayoutManager(requireContext())
        _binding?.trendingRecyclerView?.apply {
            setLayoutManager(layoutManager1)
            adapter= topSearchAdapter
        }
    }
}