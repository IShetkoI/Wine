package com.example.wine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.wine.databinding.FragmentCalculationBinding
import com.example.wine.databinding.FragmentHomeBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class Calculation : Fragment() {
    private lateinit var request: Request
    lateinit var binding: FragmentCalculationBinding
    private val dataModel: DataModel by activityViewModels()
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.bManually.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Manually_table.newInstance())
                .commit()
            Toast.makeText(activity, "Manually", Toast.LENGTH_SHORT).show()
        }
        binding.bOne.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, First_table.newInstance())
                .commit()
            Toast.makeText(activity, "First_table", Toast.LENGTH_SHORT).show()
        }
        binding.bTwo.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Second_table.newInstance())
                .commit()
            Toast.makeText(activity, "Second_table", Toast.LENGTH_SHORT).show()
        }
        binding.bThree.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Third_table.newInstance())
                .commit()
            Toast.makeText(activity, "Third_table", Toast.LENGTH_SHORT).show()
        }
        binding.bFour.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Fourth_table.newInstance())
                .commit()
            Toast.makeText(activity, "Fourth_table", Toast.LENGTH_SHORT).show()
        }
        binding.bFive.setOnClickListener{
            (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_holder, Fifth_table.newInstance())
                .commit()
            Toast.makeText(activity, "Fifth_table", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = Calculation()
    }
}