package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadsItemDB (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val source: String,
    val date: String
)
