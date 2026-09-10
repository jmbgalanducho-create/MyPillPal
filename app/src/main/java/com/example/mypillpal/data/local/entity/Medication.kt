package com.example.mypillpal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dosage: String,
    val frequency: String, // e.g., "Daily", "Every 12 hours"
    val reminderTime: String, // format "HH:mm"
    val isActive: Boolean = true,
    val startDate: Long = System.currentTimeMillis()
)
