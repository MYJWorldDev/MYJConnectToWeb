package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarksItemDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val label: String,
    val link: String
)
