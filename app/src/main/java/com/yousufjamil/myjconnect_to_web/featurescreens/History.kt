package com.yousufjamil.myjconnect_to_web.featurescreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yousufjamil.myjconnect_to_web.accessories.HistoryDataType
import com.yousufjamil.myjconnect_to_web.accessories.ListItem
import com.yousufjamil.myjconnect_to_web.data.DataSource
import com.yousufjamil.myjconnect_to_web.database.HistoryDao
import com.yousufjamil.myjconnect_to_web.database.HistoryItemDB
import com.yousufjamil.myjconnect_to_web.database.MYJConnectToWebDB
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun History() {
    var historyData by remember { mutableStateOf<List<HistoryItemDB>>(emptyList()) }

    var historyList = remember { mutableListOf<HistoryDataType>() }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        historyData = DataSource.dbProvider.historyDao.getAllHistory()
    }

    for (item in historyData) {
        historyList.add(
            HistoryDataType(
                title = item.title,
                date = item.date,
                url = item.url,
                time = item.time
            )
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 20.dp)
            .padding(20.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable {
                    DataSource.navController.popBackStack()
                }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "History",
                fontSize = 30.sp
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(20.dp)
        ) {
            var prevDate = ""

            items(historyList.size) {
                val item = historyList[historyList.size - 1 - it]

                if (prevDate != item.date) {
                    Text(
                        text = item.date,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    prevDate = item.date
                }

                ListItem(
                    title = item.title,
                    trailingText = item.time,
                    onClick = {
                        DataSource.modifyUrl = item.url
                        DataSource.navController.navigate("main")
                    },
                    trailingIcon = Icons.Default.Delete,
                    onTrailingIconClick = {
                        coroutineScope.launch {
                            DataSource.dbProvider.historyDao.deleteHistory(
                                HistoryItemDB(
                                    title = item.title,
                                    url = item.url,
                                    date = item.date,
                                    time = item.time
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}