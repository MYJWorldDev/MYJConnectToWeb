package com.yousufjamil.myjconnect_to_web.featurescreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yousufjamil.myjconnect_to_web.accessories.HistoryDataType
import com.yousufjamil.myjconnect_to_web.accessories.ListItem


@Preview(showBackground = true)
@Composable
fun History() {
    val historyList = listOf(
        HistoryDataType(
            title = "History 1",
            date = "12/12/2023"
        ),
        HistoryDataType(
            title = "History 2",
            date = "12/12/2023"
        ),
        HistoryDataType(
            title = "History 3",
            date = "12/12/2023"
        ),
        HistoryDataType(
            title = "History 4",
            date = "12/12/2023"
        ),
        HistoryDataType(
            title = "History 5",
            date = "1/1/2024"
        ),
        HistoryDataType(
            title = "History 6",
            date = "1/1/2024"
        ),
        HistoryDataType(
            title = "History 7",
            date = "1/1/2024"
        ),
        HistoryDataType(
            title = "History 8",
            date = "1/1/2024"
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "History",
                fontSize = 30.sp
            )
        }

        var prevDate = ""

        items(historyList.size) {
            val item = historyList[historyList.size -1 - it]

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
                onClick = { }
            )
        }
    }
}