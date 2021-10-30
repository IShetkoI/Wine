package com.example.wine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wine.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class Home : Fragment() {
    private lateinit var request: Request
    lateinit var binding: FragmentHomeBinding
    private val dataModel: DataModel by activityViewModels()
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bDistance.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Distance.newInstance())
                .commit()
            Toast.makeText(activity, "Distance", Toast.LENGTH_SHORT).show()
        }
        binding.bThermometer.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Temperature.newInstance())
                .commit()
            Toast.makeText(activity, "Temperature", Toast.LENGTH_SHORT).show()
        }
        binding.bHumidity.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Humidity.newInstance())
                .commit()
            Toast.makeText(activity, "Humidity", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = Home()

    }
}
