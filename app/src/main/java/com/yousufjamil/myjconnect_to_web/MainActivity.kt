package com.yousufjamil.myjconnect_to_web

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.myjconnect_to_web.data.DataSource
import com.yousufjamil.myjconnect_to_web.database.DatabaseProvider
import com.yousufjamil.myjconnect_to_web.database.MYJConnectToWebDB
import com.yousufjamil.myjconnect_to_web.featurescreens.History
import com.yousufjamil.myjconnect_to_web.ui.theme.MYJConnectToWebTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var dbProvider: MYJConnectToWebDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbProvider = DatabaseProvider.getDatabase(this)

        enableEdgeToEdge()
        setContent {
            MYJConnectToWebTheme {
                Navigation()
            }
        }
    }
    @Preview
    @Composable
    fun Navigation() {
        navController = rememberNavController()
        DataSource.navController = navController
        DataSource.dbProvider = dbProvider

        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainScreen()
            }
            composable("history") {
                History()
            }
        }
    }
}