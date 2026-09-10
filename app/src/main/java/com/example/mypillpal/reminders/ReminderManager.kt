package com.example.mypillpal.reminders

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.mypillpal.data.local.entity.Medication
import java.util.*

class ReminderManager(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleReminder(medication: Medication) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("medicationId", medication.id)
            putExtra("medicationName", medication.name)
            putExtra("dosage", medication.dosage)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medication.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            val timeParts = medication.reminderTime.split(":")
            if (timeParts.size == 2) {
                set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
                set(Calendar.MINUTE, timeParts[1].toInt())
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            
            // If the time has already passed today, or today is not a scheduled day, find the next one
            while (timeInMillis <= System.currentTimeMillis() || !isDayScheduled(this, medication.frequency)) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val alarmTime = calendar.timeInMillis

        val alarmClockInfo = AlarmManager.AlarmClockInfo(alarmTime, pendingIntent)

        try {
            Log.d("ReminderManager", "Scheduling alarm clock for ${medication.name} at $alarmTime")
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
        } catch (e: SecurityException) {
            Log.e("ReminderManager", "SecurityException while scheduling exact alarm. Falling back to inexact.", e)
            scheduleInexactAlarm(alarmTime, pendingIntent)
        } catch (e: Exception) {
            Log.e("ReminderManager", "Failed to schedule alarm for ${medication.name}", e)
        }
    }

    private fun scheduleInexactAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        try {
            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )
        } catch (e: Exception) {
            Log.e("ReminderManager", "Failed to schedule inexact alarm", e)
        }
    }

    private fun isDayScheduled(calendar: Calendar, frequency: String): Boolean {
        if (frequency == "Daily") return true
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // Sunday = 1
        val scheduledDays = frequency.split(",").mapNotNull { it.toIntOrNull() }
        return scheduledDays.contains(dayOfWeek)
    }

    fun cancelReminder(medicationId: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medicationId.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_NO_CREATE
        )
        if (pendingIntent != null) {
            alarmManager.cancel(pendingIntent)
        }
    }
}
