package com.bhaveshsp.trackmysleepquantity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sleep_quality_database")
data class Sleep(@PrimaryKey(autoGenerate = true) var nightKey:Long=0L,
                 @ColumnInfo(name = "start_time")
                 val startTime:Long=System.currentTimeMillis(),
                 @ColumnInfo(name = "end_time")
                 var endTime:Long=startTime,
                 @ColumnInfo(name = "sleep_quality")
                 var sleepQuality:Int=-1
                 ) {
}