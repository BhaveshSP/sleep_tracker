package com.bhaveshsp.trackmysleepquantity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Sleep::class)],version = 1,exportSchema = false)
abstract class SleepDatabase: RoomDatabase(){
    abstract val sleepDao:SleepDao
    companion object{
        @Volatile
        private var INSTANCE:SleepDatabase?=null

        fun getInstance(context: Context):SleepDatabase?{
            synchronized(this){
              var tempInstance= INSTANCE
                if (tempInstance==null){
                    tempInstance= Room.databaseBuilder(context.applicationContext,SleepDatabase::class.java,"sleep_history_database").fallbackToDestructiveMigration().build()
                }
                INSTANCE=tempInstance
            }
            return INSTANCE
        }
    }

}