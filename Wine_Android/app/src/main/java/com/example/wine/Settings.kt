package com.example.wine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wine.databinding.ActivityMainBinding
import com.example.wine.databinding.FragmentSettingsBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.*

class Settings : Fragment() {
    private val dataModel: DataModel by activityViewModels()

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)

        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val savedString = sharedPreferences?.getString("ip", null).toString()
        getIp(savedString)

        var value = sharedPreferences?.getFloat("min", 0F)
        binding.edMin.setText(value.toString())

        value = sharedPreferences?.getFloat("max", 0F)
        binding.edMax.setText(value.toString())

        binding.edMin.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    if(binding.edMin.text.toString()!="") {
                        val editor = sharedPreferences.edit()
                        editor?.apply {
                            putFloat("min", s.toString().toFloat())
                        }?.apply()
                    }
                }
            }
        })

        binding.edMax.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    if(binding.edMax.text.toString()!="") {
                        val editor = sharedPreferences.edit()
                        editor?.apply {
                            putFloat("max", s.toString().toFloat())
                        }?.apply()
                    }
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.saveIpButton.setOnClickListener {
            val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.apply{
                putString("ip", binding.edit.text.toString())
            }?.apply()
        }
    }

    private fun getIp(ip: String) = with(binding){
        if(ip.isNotEmpty()){
            edit.setText(ip)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Settings()

    }
}