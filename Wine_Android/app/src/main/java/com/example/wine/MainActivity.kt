package com.example.wine





import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.wine.databinding.ActivityMainBinding

import okhttp3.Request


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pref: SharedPreferences
    private lateinit var request: Request
    private val dataModel: DataModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataModel.ip.observe(this, {
            saveIp(it)
        })

        pref = getSharedPreferences("MyPref", MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout: MotionLayout = binding.startWindow
            motionLayout.transitionToEnd();
        }, 2000)


        binding.bDistance.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Distance.newInstance())
                .commit()
            Toast.makeText(this, "Distance", Toast.LENGTH_SHORT).show()
        }
        binding.bThermometer.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Temperature.newInstance())
                .commit()
            Toast.makeText(this, "Temperature", Toast.LENGTH_SHORT).show()
        }
        binding.bHumidity.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Humidity.newInstance())
                .commit()
            Toast.makeText(this, "Humidity", Toast.LENGTH_SHORT).show()
        }

        binding.bHome.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Home.newInstance())
                .commit()
        }
        binding.bSettings.setOnClickListener{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Settings.newInstance())
                .commit()
        }
    }

    private fun saveIp(ip: String){
        if(ip.isNotEmpty()) {
            val editor = pref.edit()
            editor.putString("ip", ip)
            editor.apply()
        }
    }

}