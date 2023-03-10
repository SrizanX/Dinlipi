package com.bitflecks.dinlipi.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val fabIsShown: MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }
//    = MutableLiveData()
//    init {
//        fabIsShown.value = true
//    }

}