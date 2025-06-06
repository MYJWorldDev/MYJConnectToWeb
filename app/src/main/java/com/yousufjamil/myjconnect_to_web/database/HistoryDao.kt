package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert
    suspend fun insertHistory(
        historyItemDB: HistoryItemDB
    )

    @Delete
    suspend fun deleteHistory(
        historyItemDB: HistoryItemDB
    )

    @Query("SELECT * FROM history")
    suspend fun getAllHistory(): List<HistoryItemDB>
}