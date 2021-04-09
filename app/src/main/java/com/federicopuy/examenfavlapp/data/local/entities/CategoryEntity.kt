package com.federicopuy.examenfavlapp.data.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.federicopuy.examenfavlapp.data.model.Category
import org.jetbrains.annotations.NotNull

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: Long,
    @NotNull val title: String,
    @NonNull val licenseID: Int
)

fun Category.asCategoryEntity(): CategoryEntity {
    return CategoryEntity(id, title, 0)
}

// TODO add functionality to retrieve list of questions instead of returning an empty list here
fun CategoryEntity.asCategory(): Category {
    return Category(id, title, emptyList())
}

fun List<CategoryEntity>.asListOfCategories(): List<Category> {
    return map { it.asCategory() }
}
