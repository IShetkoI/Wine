package com.example.wine

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wine.databinding.FragmentTemperatureBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class Temperature : Fragment() {
    lateinit var binding: FragmentTemperatureBinding
    private val dataModel: DataModel by activityViewModels()
    private val client = OkHttpClient()
    private lateinit var request: Request


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTemperatureBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bMeasureTemp.setOnClickListener{
            post()
        }
    }

    private fun post() {
        Thread {
            val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val savedString = sharedPreferences?.getString("ip", null).toString()
            println(savedString)
            request = Request.Builder().url("http://${savedString}/temperature").build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val resultText = response.body()?.string()
                    println(resultText)
                    activity?.runOnUiThread {
                        binding.tvValue.text = resultText +" C"
                    }
                }
            } catch (i: IOException) {
                println("NOOOOOOO")
            }

        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Temperature()

    }
}