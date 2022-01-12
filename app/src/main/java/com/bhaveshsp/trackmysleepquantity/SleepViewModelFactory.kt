package com.bhaveshsp.trackmysleepquantity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SleepViewModelFactory(private val sleepDao: SleepDao?,private val application: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SleepViewModel::class.java)){
            return SleepViewModel(sleepDao!!,application) as T

        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}