package com.example.mypillpal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mypillpal.data.local.dao.DoseLogDao
import com.example.mypillpal.data.local.dao.MedicationDao
import com.example.mypillpal.data.local.entity.DoseLog
import com.example.mypillpal.data.local.entity.Medication

@Database(entities = [Medication::class, DoseLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
    abstract fun doseLogDao(): DoseLogDao
}
