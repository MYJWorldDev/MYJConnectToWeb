package com.yousufjamil.myjconnect_to_web.data

import android.content.Context
import androidx.navigation.NavHostController
import com.yousufjamil.myjconnect_to_web.database.MYJConnectToWebDB

object DataSource {
    lateinit var navController: NavHostController
    var modifyUrl: String? = null

    lateinit var dbProvider: MYJConnectToWebDB

    lateinit var context: Context
}