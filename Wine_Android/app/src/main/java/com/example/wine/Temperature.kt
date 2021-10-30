package com.example.wine

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
            dataModel.ip.observe(activity as LifecycleOwner, {
                post(it)
            })
        }
    }

    private fun post(ip: String) {
        Toast.makeText(activity, "Post", Toast.LENGTH_SHORT).show()

        Thread {
            request = Request.Builder().url("http://${ip}/temperature").build()
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