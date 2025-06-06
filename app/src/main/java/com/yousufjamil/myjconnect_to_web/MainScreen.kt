package com.yousufjamil.myjconnect_to_web

import android.webkit.WebSettings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.yousufjamil.myjconnect_to_web.accessories.DisplayAlertDialog
import com.yousufjamil.myjconnect_to_web.accessories.DisplayCustomDialog
import com.yousufjamil.myjconnect_to_web.accessories.ListItem
import com.yousufjamil.myjconnect_to_web.accessories.ListItemDataType
import com.yousufjamil.myjconnect_to_web.data.DataSource
import com.yousufjamil.myjconnect_to_web.database.HistoryItemDB
import dev.atrii.composewebkit.ComposeWebView
import dev.atrii.composewebkit.configureWebChromeClients
import dev.atrii.composewebkit.configureWebClients
import dev.atrii.composewebkit.configureWebSettings
import dev.atrii.composewebkit.rememberComposeWebViewState
import dev.atrii.composewebkit.rememberWebViewNavigator
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    var currentUrl by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    var displayState by remember { mutableStateOf(false) }
    var titleDisplay by remember { mutableStateOf("") }
    var messageDisplay by remember { mutableStateOf("") }

    var displayMoreMenu by remember { mutableStateOf(false) }

    val appNavigator = DataSource.navController

    val initialUrl = if (DataSource.modifyUrl != null) {
        val tempUrl = DataSource.modifyUrl!!
        DataSource.modifyUrl = null
        tempUrl
    } else {
        "https://www.google.com"
    }
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    val progress = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val navigator = rememberWebViewNavigator()
    val state = rememberComposeWebViewState(
        url = initialUrl,
        onBackPress = {
            if (navigator.canGoBack())
                navigator.navigateBack()
        }
    ) {
        configureWebSettings {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        configureWebChromeClients {
            onProgressChanged { webView, newProgress ->
                isRefreshing = newProgress in 1..99
                scope.launch {
                    progress.animateTo(newProgress.toFloat())
                }
            }
            onJsAlert { webView, url, message, result ->
                titleDisplay = "$url says:"
                messageDisplay = message?.toString() ?: ""
                displayState = true

                result?.confirm()
                true
            }
        }
        configureWebClients {
            onPageStarted { webView, url, favicon ->
                isRefreshing = true
                currentUrl = url ?: "about:blank"
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val currentDate = dateFormat.format(Date())
                val currentTime = timeFormat.format(Date())

                val title = webView?.title ?: currentUrl

                coroutineScope.launch {
                    DataSource.dbProvider.historyDao.insertHistory(
                        HistoryItemDB(
                            title = title,
                            url = currentUrl,
                            date = currentDate,
                            time = currentTime
                        )
                    )
                }
            }
            onPageFinished { webView, url ->
                isRefreshing = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = currentUrl,
                onValueChange = { currentUrl = it },
                placeholder = { Text("URL") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(100.dp)),
                singleLine = true,
                colors = TextFieldDefaults.colors(

                ),
                trailingIcon = {
                    Row {
                        Button(
                            onClick = {
                                scope.launch {
                                    navigator.loadUrl(currentUrl)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.11f)
                                .aspectRatio(1f),
                            contentPadding = PaddingValues(3.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    modifier = Modifier
                                        .size(30.dp),
                                    contentScale = ContentScale.Fit,
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(7.dp))
                    }
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    displayMoreMenu = true
                },
                contentPadding = PaddingValues(3.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier
                        .size(30.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        ComposeWebView(
            modifier = Modifier,
            state = state,
            navigator = navigator,
            pull2Refresh = false,
            isRefreshing = isRefreshing,
            onRefresh = {
                scope.launch {
                    navigator.reload()
                }
            }
        )

        AnimatedVisibility(
            modifier = Modifier.fillMaxSize(),
            visible = progress.value in 1f..99f,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress.value / 100f }
                )
            }
        }

        if (displayState) {
            DisplayAlertDialog(
                title = titleDisplay,
                message = messageDisplay
            )
        }

        if (displayMoreMenu) {
            DisplayCustomDialog (
                onDismiss = {
                    displayMoreMenu = false
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    val menuItemList = listOf(
                        ListItemDataType(
                            icon = Icons.Default.DateRange,
                            title = "History",
                            onClick = {
                                displayMoreMenu = false

                                appNavigator.navigate("history")
                            }
                        ),
                        ListItemDataType(
                            icon = Icons.Default.Star,
                            title = "Bookmarks",
                            onClick = {
                                displayMoreMenu = false

                            }
                        ),
                        ListItemDataType(
                            icon = Icons.Default.CheckCircle,
                            title = "Downloads",
                            onClick = {
                                displayMoreMenu = false

                            }
                        )
                    )

                    for (item in menuItemList) {
                        ListItem(
                            icon = item.icon,
                            title = item.title,
                            onClick = item.onClick
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}