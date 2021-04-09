package com.federicopuy.examenfavlapp.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.federicopuy.examenfavlapp.data.local.entities.LicenseTypeEntity

@Dao
interface LicenseTypeDao {

    @Query("SELECT * FROM licenses")
    fun getLicenses(): List<LicenseTypeEntity>
}