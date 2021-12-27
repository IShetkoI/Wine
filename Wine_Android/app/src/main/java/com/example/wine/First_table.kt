package com.example.wine

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ddd.androidutils.DoubleClick
import com.ddd.androidutils.DoubleClickListener
import com.example.wine.databinding.FragmentFirstTableBinding
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


class First_table : Fragment() {

    lateinit var binding: FragmentFirstTableBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstTableBinding.inflate(inflater)

        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        if (sharedPreferences != null) {
            setvalue(sharedPreferences)
        }

        val doubleClick = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View?) {
            }


            override fun onDoubleClickEvent(view: View?) {
                val editor = sharedPreferences?.edit()

                editor?.apply{
                    editor.putInt("first", 1)
                    editor.putFloat("first1", 0F)
                    editor.putFloat("first2", 0F)
                    editor.putFloat("first3", 0F)
                    editor.putFloat("first4", 0F)
                    editor.putFloat("first5", 0F)
                }?.apply()
                if (sharedPreferences != null) {
                    setvalue(sharedPreferences)
                }
            }
        })

        binding.clear.setOnClickListener(doubleClick)
        binding.scroll.topEdgeEffectColor = Color.parseColor("#5AECF8")
        binding.scroll.bottomEdgeEffectColor = Color.parseColor("#63EF96C5")
        return binding.root
    }

    private fun setvalue(sp: SharedPreferences) = with(binding){
        val st = 10000.0
        var summax = 0.0
        var summad = 0.0
        var summadl = 0.0
        var ckochisl = 0.0
        var qvad = 0.0
        for(i in 1..5){
            val value = sp.getFloat("first" + (i).toString(), 0.0F)
            when(i){
                1->{binding.edFirst.text = ((value*st).roundToInt()/st).toString()
                    binding.resDelta1.text = (((value-5.0)*st).roundToInt()/st).toString()
                    binding.res1.text = ((abs(((value-5.0)/5.0)*100.0)*st).roundToInt()/st).toString()}
                2->{binding.edSecond.text = ((value*st).roundToInt()/st).toString()
                    binding.resDelta2.text = (((value-5.0)*st).roundToInt()/st).toString()
                    binding.res2.text = ((abs(((value-5.0)/5.0)*100.0)*st).roundToInt()/st).toString()}
                3->{binding.edThird.text = ((value*st).roundToInt()/st).toString()
                    binding.resDelta3.text = (((value-5.0)*st).roundToInt()/st).toString()
                    binding.res3.text = ((abs(((value-5.0)/5.0)*100.0)*st).roundToInt()/st).toString()}
                4->{binding.edFourth.text = ((value*st).roundToInt()/st).toString()
                    binding.resDelta4.text = (((value-5.0)*st).roundToInt()/st).toString()
                    binding.res4.text = ((abs(((value-5.0)/5.0)*100.0)*st).roundToInt()/st).toString()}
                5->{binding.edFifth.text = ((value*st).roundToInt()/st).toString()
                    binding.resDelta5.text = (((value-5.0)*st).roundToInt()/st).toString()
                    binding.res5.text = ((abs(((value-5.0)/5.0)*100.0)*st).roundToInt()/st).toString()}
            }
            summax+=value
            summad+=(value-5.0)
            summadl+=abs(((value-5.0)/5.0)*100.0)
        }

        binding.resX.text = (((summax/5.0)*st).roundToInt()/st).toString()
        binding.resDelta.text = (((summad/5.0)*st).roundToInt()/st).toString()
        binding.res.text = (((summadl/5.0)*st).roundToInt()/st).toString()

        for(i in 1..5){
            val value = sp.getFloat("first" + (i).toString(), 0.0F)
            if(i!=5) {
                val value1 = sp.getFloat("first" + (i + 1).toString(), 0.0F)
                qvad += ((value1-5.0) - (value-5.0)).pow(2)
            }
            ckochisl += ((value-5.0) - summad/5.0).pow(2)
            println(ckochisl)
        }

        binding.resCko.text = (((sqrt(ckochisl/4.0))*st).roundToInt()/st).toString()
        println(sqrt(ckochisl/20.0))
        try {
            binding.resQ.text = ((((qvad / 8.0)) * st).roundToInt() / st).toString()
        }
        catch (e: Exception){
            binding.resQ.text = "NaN"
        }
        try {
            binding.resV.text = ((binding.resQ.text.toString().toFloat()/binding.resCko.text.toString().toFloat()*st).roundToInt()/st).toString()
        }
        catch (e: Exception){
            binding.resV.text = "NaN"
        }
        binding.resL.text = (((summax/5.0)*st).roundToInt()/st).toString() + " ± " + (((1.96*(((sqrt(ckochisl/4.0)))/(sqrt(5.0F))))*st).roundToInt()/st).toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() =  First_table()
    }
}