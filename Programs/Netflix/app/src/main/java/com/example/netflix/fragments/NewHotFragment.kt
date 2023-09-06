package com.example.netflix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.netflix.Preferences
import com.example.netflix.R
import com.example.netflix.adapters.LargeMovieItemAdapter
import com.example.netflix.adapters.MovieFilterAdapter
import com.example.netflix.databinding.FragmentNewHotBinding
import com.example.netflix.models.MoviesModel
import com.example.netflix.models.ResponseModel
import com.example.netflix.services.ApiFunctions
import com.example.netflix.services.AppWebServices
import com.google.android.exoplayer2.util.Log

class NewHotFragment : ParentFragment() {
    private var _binding: FragmentNewHotBinding? = null
    private val largeMovieItemAdapter = LargeMovieItemAdapter()
    private val filterAdapter = MovieFilterAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewHotBinding.inflate(inflater, container, false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = _binding
        if (binding != null) {
            binding.toolbar.setOnMenuItemClickListener(toolbarListener)
            binding.toolbar.inflateMenu(R.menu.download_menu_items)
        }
        val layoutManager = LinearLayoutManager(requireContext())
        _binding?.recyclerView?.apply {
            setLayoutManager(layoutManager)
            adapter = largeMovieItemAdapter
        }
        val layoutManager1 = LinearLayoutManager(requireContext())
        layoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        _binding?.filterRecyclerView?.apply {
            setLayoutManager(layoutManager1)
            adapter = filterAdapter
        }
        fetchMovies()
    }

    private var toolbarListener = Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.open_browser -> {
                }
                R.id.cast -> {
                    Log.d("", "")

                }
                else -> {}
            }
            false
        }

    private fun fetchMovies(){
        ApiFunctions().fetchMoviesList(object:AppWebServices.OnResponseCallback{
            override fun onSuccessResponse(response: String?) {
                parseMoviesResponseData(response)
            }

            override fun onErrorResponse(response: String?) {
                parseMoviesResponseData(response)
            }
        })
    }

    private fun parseMoviesResponseData(response: String?) {
        if(!response.isNullOrEmpty()){
            val responseModel=gson.fromJson(response,MoviesResponseModel::class.java)
            if(responseModel.success){
                updateMovieList(responseModel.data)
            }

        }

    }
    private fun updateMovieList(list: ArrayList<MoviesModel>){
        largeMovieItemAdapter.mList = list
        largeMovieItemAdapter.notifyDataSetChanged()
    }
    inner class MoviesResponseModel:ResponseModel(){
        var data:ArrayList<MoviesModel> = arrayListOf()
    }
}