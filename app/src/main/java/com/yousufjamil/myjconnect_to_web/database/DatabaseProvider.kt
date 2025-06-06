package com.yousufjamil.myjconnect_to_web.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: MYJConnectToWebDB? = null

    fun getDatabase(context: Context): MYJConnectToWebDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MYJConnectToWebDB::class.java,
                "myj_connect_to_web_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
