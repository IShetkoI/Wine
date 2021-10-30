package com.example.wine

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
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
        binding.edit.setText(dataModel.ip.value)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.edit.setText(dataModel.ip.value)
        binding.saveIpButton.setOnClickListener {
            dataModel.ip.value = binding.edit.text.toString()
            Toast.makeText(activity, dataModel.ip.value, Toast.LENGTH_SHORT).show()
        }
    }



    private fun getIp(ip: String) = with(binding){
        println("2")
        if(ip.isNotEmpty()){
            edit.setText(dataModel.ip.value)
            println("3")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Settings()

    }
}