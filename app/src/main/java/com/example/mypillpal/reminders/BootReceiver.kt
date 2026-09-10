package com.example.mypillpal.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.mypillpal.PillPalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val repository = (context.applicationContext as PillPalApplication).repository
            val reminderManager = ReminderManager(context)
            
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val medications = repository.activeMedications.first()
                    medications.forEach {
                        reminderManager.scheduleReminder(it)
                    }
                } catch (e: Exception) {
                    Log.e("BootReceiver", "Failed to reschedule reminders", e)
                }
            }
        }
    }
}
