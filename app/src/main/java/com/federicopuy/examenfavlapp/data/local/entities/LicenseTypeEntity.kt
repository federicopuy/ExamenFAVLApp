package com.federicopuy.examenfavlapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "licenses")
data class LicenseTypeEntity(@PrimaryKey val id: Int, val title: String)

data class LicenseWithCategories(
    val licenseType: LicenseTypeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "licenseId"
    )
    val categories: List<CategoryEntity>
)