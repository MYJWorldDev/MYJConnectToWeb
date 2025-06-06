package com.yousufjamil.myjconnect_to_web.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookmarksDao {
    @Insert
    suspend fun insertBookmark(
        bookmarksItemDB: BookmarksItemDB
    )

    @Delete
    suspend fun deleteBookmark(
        bookmarksItemDB: BookmarksItemDB
    )

    @Query("SELECT * FROM bookmarks")
    suspend fun getAllBookmarks(): List<BookmarksItemDB>
}