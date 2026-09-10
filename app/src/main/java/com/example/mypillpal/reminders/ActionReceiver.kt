package com.example.mypillpal.reminders

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mypillpal.PillPalApplication
import com.example.mypillpal.data.local.entity.DoseLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val medicationId = intent.getLongExtra("medicationId", -1L)
        if (medicationId == -1L) return

        if (intent.action == "ACTION_TAKEN") {
            val repository = (context.applicationContext as PillPalApplication).repository
            val reminderManager = ReminderManager(context)
            CoroutineScope(Dispatchers.IO).launch {
                repository.insertLog(
                    DoseLog(
                        medicationId = medicationId,
                        timestamp = System.currentTimeMillis(),
                        status = "TAKEN"
                    )
                )
                
                // Reschedule for next occurrence
                val med = repository.getMedicationById(medicationId)
                if (med != null) {
                    reminderManager.scheduleReminder(med)
                }
            }
            
            // Cancel the notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(medicationId.toInt())
        }
    }
}
