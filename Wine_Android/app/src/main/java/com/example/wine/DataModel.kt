package com.example.wine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataModel : ViewModel() {
    val ip: MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }
}