package com.yousufjamil.myjconnect_to_web

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.myjconnect_to_web.data.DataSource
import com.yousufjamil.myjconnect_to_web.database.DatabaseProvider
import com.yousufjamil.myjconnect_to_web.database.MYJConnectToWebDB
import com.yousufjamil.myjconnect_to_web.featurescreens.Bookmarks
import com.yousufjamil.myjconnect_to_web.featurescreens.Downloads
import com.yousufjamil.myjconnect_to_web.featurescreens.History
import com.yousufjamil.myjconnect_to_web.ui.theme.MYJConnectToWebTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var dbProvider: MYJConnectToWebDB
    lateinit var context: Context
    var modifyUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbProvider = DatabaseProvider.getDatabase(this)
        context = this

        modifyUrl = intent?.dataString


        enableEdgeToEdge()
        setContent {
            MYJConnectToWebTheme {
                Navigation()
            }
        }

        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.P &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "Please allow external storage permission to allow downloads.", Toast.LENGTH_SHORT).show()

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }
    @Preview
    @Composable
    fun Navigation() {
        navController = rememberNavController()
        DataSource.navController = navController
        DataSource.dbProvider = dbProvider
        DataSource.context = context
        DataSource.modifyUrl = modifyUrl

        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainScreen()
            }
            composable("history") {
                History()
            }
            composable("bookmarks") {
                Bookmarks()
            }
            composable("downloads") {
                Downloads()
            }
        }
    }
}