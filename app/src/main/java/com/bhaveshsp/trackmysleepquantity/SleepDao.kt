package com.bhaveshsp.trackmysleepquantity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SleepDao {
    @Insert
    fun insert(sleep:Sleep)

    @Update
    fun update(sleep:Sleep)

    @Query("SELECT * from sleep_quality_database where nightKey=:key")
    fun get(key:Long):LiveData<Sleep>

    @Query("DELETE FROM sleep_quality_database")
    fun clear()

    @Query("SELECT * from sleep_quality_database ORDER BY nightKey DESC LIMIT 1")
    fun getTonight():Sleep?


    @Query("SELECT * from sleep_quality_database ORDER By nightKey DESC")
    fun getAllNight():LiveData<List<Sleep>>

}