package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        HistoryItemDB::class,
        DownloadsItemDB::class,
        BookmarksItemDB::class
    ],
    version = 1
)
abstract class MYJConnectToWebDB: RoomDatabase() {
    abstract val historyDao: HistoryDao
    abstract val downloadsDao: DownloadsDao
    abstract val bookmarksDao: BookmarksDao
}