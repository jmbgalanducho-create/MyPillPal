package com.example.mypillpal.data.repository

import com.example.mypillpal.data.local.dao.DoseLogDao
import com.example.mypillpal.data.local.dao.MedicationDao
import com.example.mypillpal.data.local.entity.DoseLog
import com.example.mypillpal.data.local.entity.Medication
import kotlinx.coroutines.flow.Flow

class MedicationRepository(
    private val medicationDao: MedicationDao,
    private val doseLogDao: DoseLogDao
) {
    val allMedications: Flow<List<Medication>> = medicationDao.getAllMedications()
    val activeMedications: Flow<List<Medication>> = medicationDao.getActiveMedications()
    val allLogs: Flow<List<DoseLog>> = doseLogDao.getAllLogs()

    suspend fun getMedicationById(id: Long): Medication? {
        return medicationDao.getMedicationById(id)
    }

    suspend fun insertMedication(medication: Medication): Long {
        return medicationDao.insertMedication(medication)
    }

    suspend fun updateMedication(medication: Medication) {
        medicationDao.updateMedication(medication)
    }

    suspend fun deleteMedication(medication: Medication) {
        medicationDao.deleteMedication(medication)
    }

    fun getLogsForMedication(medicationId: Long): Flow<List<DoseLog>> {
        return doseLogDao.getLogsForMedication(medicationId)
    }

    suspend fun insertLog(log: DoseLog) {
        doseLogDao.insertLog(log)
    }

    suspend fun deleteLog(log: DoseLog) {
        doseLogDao.deleteLog(log)
    }
}
