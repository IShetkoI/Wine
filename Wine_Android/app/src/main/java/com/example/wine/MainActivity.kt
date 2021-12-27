package com.example.wine





import android.content.SharedPreferences
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
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
    private var soundPool: SoundPool? = null
    private val soundId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Wine_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataModel.ip.observe(this, {
            saveIp(it)
        })

        pref = getSharedPreferences("MyPref", MODE_PRIVATE)

        if (pref.getBoolean("firstrun", true)) {
            val editor = pref.edit()

            editor?.apply{
                putString("ip", "")
                putString("history", "")

                putFloat("min", 0F)
                putFloat("max", 0F)

                editor.putInt("first", 1)
                editor.putInt("second", 1)
                editor.putInt("third", 1)
                editor.putInt("fourth", 1)
                editor.putInt("fifth", 1)

                editor.putFloat("first1", 0F)
                editor.putFloat("first2", 0F)
                editor.putFloat("first3", 0F)
                editor.putFloat("first4", 0F)
                editor.putFloat("first5", 0F)

                editor.putFloat("second1", 0F)
                editor.putFloat("second2", 0F)
                editor.putFloat("second3", 0F)
                editor.putFloat("second4", 0F)
                editor.putFloat("second5", 0F)

                editor.putFloat("third1", 0F)
                editor.putFloat("third2", 0F)
                editor.putFloat("third3", 0F)
                editor.putFloat("third4", 0F)
                editor.putFloat("third5", 0F)

                editor.putFloat("fourth1", 0F)
                editor.putFloat("fourth2", 0F)
                editor.putFloat("fourth3", 0F)
                editor.putFloat("fourth4", 0F)
                editor.putFloat("fourth5", 0F)

                editor.putFloat("fifth1", 0F)
                editor.putFloat("fifth2", 0F)
                editor.putFloat("fifth3", 0F)
                editor.putFloat("fifth4", 0F)
                editor.putFloat("fifth5", 0F)

                editor.putString("man_head", "0")
                editor.putString("man_1", "0")
                editor.putString("man_2", "0")
                editor.putString("man_3", "0")
                editor.putString("man_4", "0")
                editor.putString("man_5", "0")
            }?.apply()


            pref.edit().putBoolean("firstrun", false).apply();
        }

        soundPool = SoundPool(6, AudioManager.STREAM_MUSIC, 0)
        soundPool!!.load(baseContext, R.raw.wine, 1)

        Handler(Looper.getMainLooper()).postDelayed({
            soundPool?.play(soundId, 1F, 1F, 0, 0, 1F)

        }, 1130)
        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout: MotionLayout = binding.startWindow
            motionLayout.transitionToEnd()

        }, 2000)


        binding.bDistance.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Distance.newInstance())
                .commit()
        }
        binding.bThermometer.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Temperature.newInstance())
                .commit()
        }
        binding.bHumidity.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Humidity.newInstance())
                .commit()
        }



        binding.bHome.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            binding.bHome.setShadowColorLight(Color.parseColor("#D5FAFD"))
            binding.bHome.setShadowColorDark(Color.parseColor("#63EF96C5"))

            binding.bSettings.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bSettings.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bCalc.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bCalc.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bHistory.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHistory.setShadowColorDark(Color.parseColor("#43556F9A"))

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Home.newInstance())
                .commit()
        }
        binding.bSettings.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            binding.bSettings.setShadowColorLight(Color.parseColor("#D5FAFD"))
            binding.bSettings.setShadowColorDark(Color.parseColor("#63EF96C5"))

            binding.bHome.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHome.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bCalc.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bCalc.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bHistory.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHistory.setShadowColorDark(Color.parseColor("#43556F9A"))

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Settings.newInstance())
                .commit()
        }
        binding.bCalc.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            binding.bCalc.setShadowColorLight(Color.parseColor("#D5FAFD"))
            binding.bCalc.setShadowColorDark(Color.parseColor("#63EF96C5"))

            binding.bHome.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHome.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bSettings.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bSettings.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bHistory.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHistory.setShadowColorDark(Color.parseColor("#43556F9A"))

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Calculation.newInstance())
                .commit()
        }
        binding.bHistory.setOnClickListener{
            binding.bDistance.visibility = View.GONE
            binding.bThermometer.visibility = View.GONE
            binding.bHumidity.visibility = View.GONE
            binding.bHistory.setShadowColorLight(Color.parseColor("#D5FAFD"))
            binding.bHistory.setShadowColorDark(Color.parseColor("#63EF96C5"))

            binding.bHome.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bHome.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bSettings.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bSettings.setShadowColorDark(Color.parseColor("#43556F9A"))

            binding.bCalc.setShadowColorLight(Color.parseColor("#FFFFFF"))
            binding.bCalc.setShadowColorDark(Color.parseColor("#43556F9A"))

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, History.newInstance())
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