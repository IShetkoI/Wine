package com.example.wine

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.wine.databinding.FragmentFirstTableBinding
import com.example.wine.databinding.FragmentManuallyTableBinding
import com.example.wine.databinding.FragmentSecondTableBinding
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt


class Manually_table : Fragment() {

    lateinit var binding: FragmentManuallyTableBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManuallyTableBinding.inflate(inflater)
        val sharedPreferences = this.activity?.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        var value = sharedPreferences?.getString("man_head", "0")
        binding.edHeader.setText(value)
        value = sharedPreferences?.getString("man_1", "0")
        binding.edFirst.setText(value)
        value = sharedPreferences?.getString("man_2", "0")
        binding.edSecond.setText(value)
        value = sharedPreferences?.getString("man_3", "0")
        binding.edThird.setText(value)
        value = sharedPreferences?.getString("man_4", "0")
        binding.edFourth.setText(value)
        value = sharedPreferences?.getString("man_5", "0")
        binding.edFifth.setText(value)

        if (sharedPreferences != null) {
            setvalue(sharedPreferences)
        }

        binding.edHeader.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_head", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        binding.edFirst.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_1", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        binding.edSecond.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_2", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        binding.edThird.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_3", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        binding.edFourth.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_4", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        binding.edFifth.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (sharedPreferences != null && s != "") {
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("man_5", s.toString())
                    }?.apply()

                    setvalue(sharedPreferences)
                }
            }
        })

        if(binding.edHeader.text.toString() == "" ) {
            binding.edHeader.setText("0")
        }
        if(binding.edFifth.text.toString() == "" ) {
            binding.edFifth.setText("0")
        }
        if(binding.edFourth.text.toString() == "" ) {
            binding.edFourth.setText("0")
        }
        if(binding.edFirst.text.toString() == "" ) {
            binding.edFirst.setText("0")
        }
        if(binding.edSecond.text.toString() == "" ) {
            binding.edSecond.setText("0")
        }
        if(binding.edThird.text.toString() == "" ) {
            binding.edThird.setText("0")
        }
        binding.scroll.topEdgeEffectColor = Color.parseColor("#5AECF8")

        binding.scroll.bottomEdgeEffectColor = Color.parseColor("#63EF96C5")

        return binding.root
    }

    private fun setvalue(sp: SharedPreferences) = with(binding){
        val st = 10000.0
        if(binding.edHeader.text.toString() != "" && binding.edHeader.text.toString() != "0" && binding.edFirst.text.toString() != ""  && binding.edSecond.text.toString() != ""  && binding.edThird.text.toString() != "" && binding.edFourth.text.toString() != "" && binding.edFifth.text.toString() != "") {
            binding.resDelta1.text =
                (((binding.edFirst.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) * st).roundToInt() / st).toString()
            binding.res1.text = ((abs(
                ((binding.edFirst.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) / binding.edHeader.text.toString().toFloat()) * 100.0
            ) * st).roundToInt() / st).toString()

            binding.resDelta2.text =
                (((binding.edSecond.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) * st).roundToInt() / st).toString()
            binding.res2.text = ((abs(
                ((binding.edSecond.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) / binding.edHeader.text.toString().toFloat()) * 100.0
            ) * st).roundToInt() / st).toString()

            binding.resDelta3.text =
                (((binding.edThird.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) * st).roundToInt() / st).toString()
            binding.res3.text = ((abs(
                ((binding.edThird.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) / binding.edHeader.text.toString().toFloat()) * 100.0
            ) * st).roundToInt() / st).toString()

            binding.resDelta4.text =
                (((binding.edFourth.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) * st).roundToInt() / st).toString()
            binding.res4.text = ((abs(
                ((binding.edFourth.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) / binding.edHeader.text.toString().toFloat()) * 100.0
            ) * st).roundToInt() / st).toString()

            binding.resDelta5.text =
                (((binding.edFifth.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) * st).roundToInt() / st).toString()
            binding.res5.text = ((abs(
                ((binding.edFifth.text.toString().toFloat() - binding.edHeader.text.toString()
                    .toFloat()) / binding.edHeader.text.toString().toFloat()) * 100.0
            ) * st).roundToInt() / st).toString()

            binding.resX.text = ((((binding.edFirst.text.toString().toFloat()
                    + binding.edSecond.text.toString().toFloat()
                    + binding.edThird.text.toString().toFloat()
                    + binding.edFourth.text.toString().toFloat()
                    + binding.edFifth.text.toString()
                .toFloat() )/ 5.0F) * st).roundToInt() / st).toString()


            binding.resDelta.text = ((((binding.resDelta1.text.toString().toFloat()
                    + binding.resDelta2.text.toString().toFloat()
                    + binding.resDelta3.text.toString().toFloat()
                    + binding.resDelta4.text.toString().toFloat()
                    + binding.resDelta5.text.toString().toFloat() )/ 5.0F) * st).roundToInt() / st).toString()


            binding.res.text = ((((binding.res1.text.toString().toFloat()
                    + binding.res2.text.toString().toFloat()
                    + binding.res3.text.toString().toFloat()
                    + binding.res4.text.toString().toFloat()
                    + binding.res5.text.toString()
                .toFloat() )/ 5.0F) * st).roundToInt() / st).toString()


            binding.resCko.text = (((sqrt(
                ((binding.resDelta1.text.toString().toFloat() - binding.resDelta.text.toString()
                    .toFloat()).pow(2) +
                        (binding.resDelta2.text.toString()
                            .toFloat() - binding.resDelta.text.toString().toFloat()).pow(2) +
                        (binding.resDelta3.text.toString()
                            .toFloat() - binding.resDelta.text.toString().toFloat()).pow(2) +
                        (binding.resDelta4.text.toString()
                            .toFloat() - binding.resDelta.text.toString().toFloat()).pow(2) +
                        (binding.resDelta5.text.toString()
                            .toFloat() - binding.resDelta.text.toString().toFloat()).pow(2)) / 4.0F
            )) * st).roundToInt() / st).toString()

            try {
                binding.resQ.text = ((((((binding.resDelta2.text.toString()
                    .toFloat() - binding.resDelta1.text.toString().toFloat()).pow(2) +
                        (binding.resDelta3.text.toString()
                            .toFloat() - binding.resDelta2.text.toString().toFloat()).pow(2) +
                        (binding.resDelta4.text.toString()
                            .toFloat() - binding.resDelta3.text.toString().toFloat()).pow(2) +
                        (binding.resDelta5.text.toString()
                            .toFloat() - binding.resDelta4.text.toString()
                            .toFloat()).pow(2)) / 8.0F)) * st).roundToInt() / st).toString()
            } catch (e: Exception) {
                binding.resQ.text = "NaN"
            }
            try {
                binding.resV.text =
                    ((((binding.resQ.text.toString().toFloat()) / (binding.resCko.text.toString()
                        .toFloat())) * st).roundToInt() / st).toString()
            } catch (e: Exception) {
                binding.resV.text = "NaN"
            }
            binding.resL.text = ((binding.resX.text.toString()
                .toFloat() * st).roundToInt() / st).toString() + " ± " + (((1.96 * (binding.resCko.text.toString()
                .toFloat()) / (sqrt(5.0F))) * st).roundToInt() / st).toString()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Manually_table()
    }
}