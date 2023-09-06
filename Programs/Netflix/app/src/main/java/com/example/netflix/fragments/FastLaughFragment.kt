package com.example.netflix.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.netflix.databinding.FragmentFastLaughtBinding

class FastLaughFragment:ParentFragment() {
    private var _binding: FragmentFastLaughtBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFastLaughtBinding.inflate(inflater, container, false)
        return _binding?.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}