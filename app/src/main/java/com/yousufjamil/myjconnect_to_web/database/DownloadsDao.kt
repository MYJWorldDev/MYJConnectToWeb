package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DownloadsDao {
    @Insert
    suspend fun insertDownload(
        downloadsItemDB: DownloadsItemDB
    )

    @Delete
    suspend fun deleteDownload(
        downloadsItemDB: DownloadsItemDB
    )

    @Query("SELECT * FROM downloads")
    suspend fun getAllDownloads(): List<DownloadsItemDB>
}