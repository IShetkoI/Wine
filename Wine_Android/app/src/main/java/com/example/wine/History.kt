package com.example.wine

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.wine.databinding.FragmentHistoryBinding
import com.example.wine.databinding.FragmentSettingsBinding


class History : Fragment() {
    lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater)

        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = History()

    }
}