package com.bhaveshsp.trackmysleepquantity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SleepQualityViewModelFactory(private val nightKey:Long=0L,private val sleepDao: SleepDao):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)){
            return SleepQualityViewModel(nightKey,sleepDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")

    }
}