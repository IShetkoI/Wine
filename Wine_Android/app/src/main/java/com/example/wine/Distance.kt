package com.example.wine

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wine.databinding.FragmentDistanceBinding
import com.example.wine.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Distance : Fragment() {
    lateinit var binding: FragmentDistanceBinding
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
        binding = FragmentDistanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bMeasureDistance.setOnClickListener{
            post()
        }
    }

    private fun post() {
        Thread {
            val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val savedString = sharedPreferences?.getString("ip", null).toString()
            val sdf = SimpleDateFormat("kk:mm:ss-dd/M/yyyy")
            val currentDate = sdf.format(Date())
            request = Request.Builder().url("http://${savedString}/distance/date-${currentDate}").build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val resultText = response.body()?.string()
                    val value = resultText?.toFloat()
                    if (value != null) {
                        val one = 23.0
                        val two = 59.0
                        val three = 95.0
                        val four = 131.5
                        val five = 160.0
                        if(value<=one) {
                            var last = sharedPreferences?.getInt("first", 1)
                            if (last == 6){
                                last = 1
                            }
                            val editor = sharedPreferences?.edit()
                            editor?.apply{
                                putFloat("first" + last.toString(), value)
                                putInt("first", last!!+1)
                            }?.apply()
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(activity, "1-"+last.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        if(one<value && value<=two) {
                            var last = sharedPreferences?.getInt("second", 1)
                            if(last==6) {
                                last = 1
                            }
                            val editor = sharedPreferences?.edit()
                            editor?.apply{
                                putFloat("second" + last.toString(), value)
                                putInt("second", last!!+1)
                            }?.apply()
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(activity, "2-"+last.toString(), Toast.LENGTH_SHORT).show()
                            }

                        }

                        if(two<value && value<=three) {
                            var last = sharedPreferences?.getInt("third", 1)
                            if(last==6) {
                                last = 1
                            }
                            val editor = sharedPreferences?.edit()
                            editor?.apply{
                                putFloat("third" + last.toString(), value)
                                putInt("third", last!!+1)
                            }?.apply()
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(activity, "3-"+last.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        if(three<value && value<=four) {
                            var last = sharedPreferences?.getInt("fourth", 1)
                            if(last==6) {
                                last = 1
                            }
                            val editor = sharedPreferences?.edit()
                            editor?.apply{
                                putFloat("fourth" + last.toString(), value)
                                putInt("fourth", last!!+1)
                            }?.apply()
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(activity, "4-"+last.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        if(four<value && value<=five) {
                            var last = sharedPreferences?.getInt("fifth", 1)
                            if(last==6) {
                                last = 1
                            }
                            val editor = sharedPreferences?.edit()
                            editor?.apply{
                                putFloat("fifth" + last.toString(), value)
                                putInt("fifth", last!!+1)
                            }?.apply()
                            Handler(Looper.getMainLooper()).post {
                                Toast.makeText(activity, "5-"+last.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    activity?.runOnUiThread {
                        binding.tvValue.text = resultText +"\nсм"
                    }
                }
            } catch (i: IOException) {

            }

        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = Distance()

    }
}