package com.example.wine

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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

class Settings : Fragment() {
    private val dataModel: DataModel by activityViewModels()
    private lateinit var request: Request

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)

        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val savedString = sharedPreferences?.getString("ip", null).toString()
        getIp(savedString)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.saveIpButton.setOnClickListener {
            val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.apply{
                putString("ip", binding.edit.text.toString())
            }?.apply()
            Toast.makeText(activity, binding.edit.text.toString(), Toast.LENGTH_SHORT).show()
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