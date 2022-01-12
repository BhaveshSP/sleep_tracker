package com.bhaveshsp.trackmysleepquantity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*

class SleepViewModel(private val  sleepDao: SleepDao,application: Application):AndroidViewModel(application) {
    private var viewModelJob= Job()
    private val scope= CoroutineScope(Dispatchers.Main+viewModelJob)
    private val tonight= MutableLiveData<Sleep?>()
    //SnakeBar
    private var _showSnackBar=MutableLiveData<Boolean>()
    val showSnackBar:LiveData<Boolean>
    get() = _showSnackBar


    private val _navigateToSleepQuality=MutableLiveData<Sleep>()
    val navigateToSleepQuality:LiveData<Sleep>
    get() = _navigateToSleepQuality

    val night=sleepDao.getAllNight()
    val nightString=Transformations.map(night){
        nights->
        formatNights(nights,application.resources)
    }
    init {
        initializeTonight()
    }
    //Buttons Visibility

   // val startButtonVisibility=Transformations.map(tonight){
    //    it==null
    //}
    val stopButtonVisibility=Transformations.map(tonight){
        it!=null
    }
    val clearButtonVisibility=Transformations.map(night){
        it?.isNotEmpty()
    }

    fun doneNavigation(){
        _navigateToSleepQuality.value=null
    }

    fun doneShowingSnackBar(){
        _showSnackBar.value=false
    }

    private fun initializeTonight(){
        scope.launch {
            tonight.value=getTonightFromDatabase()
        }
    }
    private suspend fun getTonightFromDatabase():Sleep?{
        return withContext(Dispatchers.IO){
            var night=sleepDao.getTonight()
            if(night?.endTime!=night?.startTime){
                null
            }
            night
        }
    }
    //Start tracking
    fun onStartTracking(){
        scope.launch {
            val newNight=Sleep()
            insert(newNight)
            tonight.value=getTonightFromDatabase()

        }
    }
    private suspend fun insert(night:Sleep){
        withContext(Dispatchers.IO){
            sleepDao.insert(night)
        }
    }

    //Stop Tracking
    fun onStopTracking(){
        scope.launch {
            val oldNight=tonight.value ?:return@launch
            oldNight.endTime=System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value=oldNight
        }
    }
    private suspend fun update(night: Sleep){
        withContext(Dispatchers.IO){
            sleepDao.update(night)
        }

    }
    //Clear Database
    fun onClear(){
        scope.launch {
            clear()
            tonight.value=null
        }
    }
    private suspend fun clear(){
        withContext(Dispatchers.IO){
            sleepDao.clear()

    }
    }

    override fun onCleared() {
        super.onCleared()
        scope.launch{
            _showSnackBar.value=true
        }
        viewModelJob.cancel()
    }
}