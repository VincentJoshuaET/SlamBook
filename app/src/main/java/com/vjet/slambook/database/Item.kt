package com.vjet.slambook.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    val fullName: String,
    val nickname: String,
    val address: String,
    val mobile: String,
    val email: String,
    val gender: String,
    val birthday: Long,
    val age: Int,
    val occupation: String,
    val language: String,
    val color: String,
    val musician: String,
    val movie: String,
    val tvShow: String,
    val actor: String,
    val book: String,
    val author: String,
    val game: String,
    val food: String,
    val place: String,
    val card: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}