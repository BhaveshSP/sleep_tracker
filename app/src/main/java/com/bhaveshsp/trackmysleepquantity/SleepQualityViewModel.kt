package com.bhaveshsp.trackmysleepquantity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class SleepQualityViewModel(private val nightKey:Long=0L,private val sleepDao: SleepDao):ViewModel() {
    private val _navigateToSleepTracker=MutableLiveData<Boolean?>()
    val navigateToSleepTracker:LiveData<Boolean?>
    get() = _navigateToSleepTracker
    private val viewModelJob= Job()
    private val scope= CoroutineScope(Dispatchers.Main +viewModelJob)
    fun doneNavigation(){
        _navigateToSleepTracker.value=null
    }
    fun onSetSleepQuality(quality:Int){
        scope.launch {
            withContext(Dispatchers.IO){
                val tonight=sleepDao.getTonight()?:return@withContext
                tonight.sleepQuality=quality
                sleepDao.update(tonight)

            }
            _navigateToSleepTracker.value=true
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}