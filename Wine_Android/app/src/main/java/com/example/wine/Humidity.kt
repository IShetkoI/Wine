package com.example.wine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wine.databinding.FragmentHumidityBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class Humidity : Fragment() {
    lateinit var binding: FragmentHumidityBinding
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
        binding = FragmentHumidityBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bMeasureHumidity.setOnClickListener{
            dataModel.ip.observe(activity as LifecycleOwner, {
                post(it)
            })
        }
    }

    private fun post(ip: String) {


        Thread {
            request = Request.Builder().url("http://${ip}/humidity").build()
            try {
                println("1")

                val response = client.newCall(request).execute()
                println("2")
                if (response.isSuccessful) {
                    println("3")
                    val resultText = response.body()?.string()
                    println(resultText)
                    activity?.runOnUiThread {
                        binding.tvValue.text = resultText
                        Toast.makeText(activity, resultText.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (i: IOException) {
                println("Пиздкец")
            }

        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Distance()

    }
}