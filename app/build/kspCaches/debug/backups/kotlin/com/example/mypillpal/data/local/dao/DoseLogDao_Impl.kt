package com.example.mypillpal.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.mypillpal.`data`.local.entity.DoseLog
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class DoseLogDao_Impl(
  __db: RoomDatabase,
) : DoseLogDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfDoseLog: EntityInsertAdapter<DoseLog>

  private val __deleteAdapterOfDoseLog: EntityDeleteOrUpdateAdapter<DoseLog>
  init {
    this.__db = __db
    this.__insertAdapterOfDoseLog = object : EntityInsertAdapter<DoseLog>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `dose_logs` (`id`,`medicationId`,`timestamp`,`status`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: DoseLog) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.medicationId)
        statement.bindLong(3, entity.timestamp)
        statement.bindText(4, entity.status)
      }
    }
    this.__deleteAdapterOfDoseLog = object : EntityDeleteOrUpdateAdapter<DoseLog>() {
      protected override fun createQuery(): String = "DELETE FROM `dose_logs` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: DoseLog) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertLog(log: DoseLog): Unit = performSuspending(__db, false, true) {
      _connection ->
    __insertAdapterOfDoseLog.insert(_connection, log)
  }

  public override suspend fun deleteLog(log: DoseLog): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfDoseLog.handle(_connection, log)
  }

  public override fun getLogsForMedication(medicationId: Long): Flow<List<DoseLog>> {
    val _sql: String = "SELECT * FROM dose_logs WHERE medicationId = ? ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("dose_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, medicationId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfMedicationId: Int = getColumnIndexOrThrow(_stmt, "medicationId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MutableList<DoseLog> = mutableListOf()
        while (_stmt.step()) {
          val _item: DoseLog
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpMedicationId: Long
          _tmpMedicationId = _stmt.getLong(_columnIndexOfMedicationId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _item = DoseLog(_tmpId,_tmpMedicationId,_tmpTimestamp,_tmpStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllLogs(): Flow<List<DoseLog>> {
    val _sql: String = "SELECT * FROM dose_logs ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("dose_logs")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfMedicationId: Int = getColumnIndexOrThrow(_stmt, "medicationId")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MutableList<DoseLog> = mutableListOf()
        while (_stmt.step()) {
          val _item: DoseLog
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpMedicationId: Long
          _tmpMedicationId = _stmt.getLong(_columnIndexOfMedicationId)
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _item = DoseLog(_tmpId,_tmpMedicationId,_tmpTimestamp,_tmpStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
