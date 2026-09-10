package com.example.mypillpal

import android.app.Application
import androidx.room.Room
import com.example.mypillpal.data.local.AppDatabase
import com.example.mypillpal.data.repository.MedicationRepository

class PillPalApplication : Application() {
    private val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "pillpal_database"
        ).build()
    }

    val repository by lazy {
        MedicationRepository(database.medicationDao(), database.doseLogDao())
    }
}
