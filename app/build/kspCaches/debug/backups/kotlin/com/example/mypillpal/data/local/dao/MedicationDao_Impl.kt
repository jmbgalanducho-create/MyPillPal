package com.example.mypillpal.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.example.mypillpal.`data`.local.entity.Medication
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class MedicationDao_Impl(
  __db: RoomDatabase,
) : MedicationDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMedication: EntityInsertAdapter<Medication>

  private val __deleteAdapterOfMedication: EntityDeleteOrUpdateAdapter<Medication>

  private val __updateAdapterOfMedication: EntityDeleteOrUpdateAdapter<Medication>
  init {
    this.__db = __db
    this.__insertAdapterOfMedication = object : EntityInsertAdapter<Medication>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `medications` (`id`,`name`,`dosage`,`frequency`,`reminderTime`,`isActive`,`startDate`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: Medication) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.dosage)
        statement.bindText(4, entity.frequency)
        statement.bindText(5, entity.reminderTime)
        val _tmp: Int = if (entity.isActive) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.startDate)
      }
    }
    this.__deleteAdapterOfMedication = object : EntityDeleteOrUpdateAdapter<Medication>() {
      protected override fun createQuery(): String = "DELETE FROM `medications` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Medication) {
        statement.bindLong(1, entity.id)
      }
    }
    this.__updateAdapterOfMedication = object : EntityDeleteOrUpdateAdapter<Medication>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `medications` SET `id` = ?,`name` = ?,`dosage` = ?,`frequency` = ?,`reminderTime` = ?,`isActive` = ?,`startDate` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: Medication) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.dosage)
        statement.bindText(4, entity.frequency)
        statement.bindText(5, entity.reminderTime)
        val _tmp: Int = if (entity.isActive) 1 else 0
        statement.bindLong(6, _tmp.toLong())
        statement.bindLong(7, entity.startDate)
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun insertMedication(medication: Medication): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfMedication.insertAndReturnId(_connection, medication)
    _result
  }

  public override suspend fun deleteMedication(medication: Medication): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfMedication.handle(_connection, medication)
  }

  public override suspend fun updateMedication(medication: Medication): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfMedication.handle(_connection, medication)
  }

  public override fun getAllMedications(): Flow<List<Medication>> {
    val _sql: String = "SELECT * FROM medications ORDER BY name ASC"
    return createFlow(__db, false, arrayOf("medications")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDosage: Int = getColumnIndexOrThrow(_stmt, "dosage")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _result: MutableList<Medication> = mutableListOf()
        while (_stmt.step()) {
          val _item: Medication
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDosage: String
          _tmpDosage = _stmt.getText(_columnIndexOfDosage)
          val _tmpFrequency: String
          _tmpFrequency = _stmt.getText(_columnIndexOfFrequency)
          val _tmpReminderTime: String
          _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          val _tmpIsActive: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp != 0
          val _tmpStartDate: Long
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate)
          _item =
              Medication(_tmpId,_tmpName,_tmpDosage,_tmpFrequency,_tmpReminderTime,_tmpIsActive,_tmpStartDate)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveMedications(): Flow<List<Medication>> {
    val _sql: String = "SELECT * FROM medications WHERE isActive = 1"
    return createFlow(__db, false, arrayOf("medications")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDosage: Int = getColumnIndexOrThrow(_stmt, "dosage")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _result: MutableList<Medication> = mutableListOf()
        while (_stmt.step()) {
          val _item: Medication
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDosage: String
          _tmpDosage = _stmt.getText(_columnIndexOfDosage)
          val _tmpFrequency: String
          _tmpFrequency = _stmt.getText(_columnIndexOfFrequency)
          val _tmpReminderTime: String
          _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          val _tmpIsActive: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp != 0
          val _tmpStartDate: Long
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate)
          _item =
              Medication(_tmpId,_tmpName,_tmpDosage,_tmpFrequency,_tmpReminderTime,_tmpIsActive,_tmpStartDate)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMedicationById(id: Long): Medication? {
    val _sql: String = "SELECT * FROM medications WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDosage: Int = getColumnIndexOrThrow(_stmt, "dosage")
        val _columnIndexOfFrequency: Int = getColumnIndexOrThrow(_stmt, "frequency")
        val _columnIndexOfReminderTime: Int = getColumnIndexOrThrow(_stmt, "reminderTime")
        val _columnIndexOfIsActive: Int = getColumnIndexOrThrow(_stmt, "isActive")
        val _columnIndexOfStartDate: Int = getColumnIndexOrThrow(_stmt, "startDate")
        val _result: Medication?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDosage: String
          _tmpDosage = _stmt.getText(_columnIndexOfDosage)
          val _tmpFrequency: String
          _tmpFrequency = _stmt.getText(_columnIndexOfFrequency)
          val _tmpReminderTime: String
          _tmpReminderTime = _stmt.getText(_columnIndexOfReminderTime)
          val _tmpIsActive: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsActive).toInt()
          _tmpIsActive = _tmp != 0
          val _tmpStartDate: Long
          _tmpStartDate = _stmt.getLong(_columnIndexOfStartDate)
          _result =
              Medication(_tmpId,_tmpName,_tmpDosage,_tmpFrequency,_tmpReminderTime,_tmpIsActive,_tmpStartDate)
        } else {
          _result = null
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
