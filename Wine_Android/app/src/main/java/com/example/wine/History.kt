package com.example.wine

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import com.example.wine.databinding.FragmentHistoryBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class History : Fragment() {
    lateinit var binding: FragmentHistoryBinding
    private val client = OkHttpClient()
    private lateinit var request: Request

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHistoryBinding.inflate(inflater)
        binding.scrollView2.topEdgeEffectColor = Color.parseColor("#5AECF8")
        binding.scrollView2.bottomEdgeEffectColor = Color.parseColor("#63EF96C5")


        binding.btsync.setOnClickListener {
            post()
        }
        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var value = sharedPreferences?.getString("history", "")
        binding.tvHistory.text = value

        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
            }


            override fun onDoubleClickEvent(view: View?) {
                val editor = sharedPreferences?.edit()

                editor?.apply{
                    editor.putString("history", "")
                }?.apply()
                value = sharedPreferences?.getString("history", "")
                binding.tvHistory.text = value
            }
        })

        binding.bttrash.setOnClickListener(doubleClick)
        binding.scrollView2.post {
            binding.scrollView2.fullScroll(View.FOCUS_DOWN)
        }

        return binding.root
    }

    private fun post() {
        Thread {
            val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val savedString = sharedPreferences?.getString("ip", null).toString()
            request = Request.Builder().url("http://${savedString}/history").build()
            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val value = response.body()?.string()

                    val editor = sharedPreferences?.edit()

                    editor?.apply{
                        putString("history", value)
                    }?.apply()
                }

            }catch (i: IOException) {

            }
            activity?.runOnUiThread {
                val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val value = sharedPreferences?.getString("history", "")
                binding.tvHistory.text = value
                binding.scrollView2.post {
                    binding.scrollView2.fullScroll(View.FOCUS_DOWN)
                }
            }

        }.start()
    }

    companion object {
        @JvmStatic
        fun newInstance() = History()

    }
}