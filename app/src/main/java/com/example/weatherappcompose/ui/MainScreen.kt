package com.example.weatherappcompose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherappcompose.R
import com.example.weatherappcompose.data.model.WeatherData
import com.example.weatherappcompose.data.model.Hour
import com.example.weatherappcompose.ui.theme.BlueLight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainCard(
    currentDay: MutableState<WeatherData>,
    onClickSync: () -> Unit,
    onClickSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(BlueLight),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currentDay.value.time,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 7.dp, start = 8.dp)
                    )
                    AsyncImage(
                        model = "https:${currentDay.value.icon}",
                        contentDescription = "currentWeatherImg",
                        modifier = Modifier
                            .size(35.dp)
                            .padding(top = 3.dp, end = 8.dp)
                    )
                }
                Text(
                    text = currentDay.value.city,
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = if (currentDay.value.currentTemp.isNotEmpty()) {
                        "${currentDay.value.currentTemp.toFloat().toInt()}°C"
                    } else {
                        if (currentDay.value.maxTemp.isNotEmpty() && currentDay.value.minTemp.isNotEmpty()) {
                            "${currentDay.value.maxTemp.toFloat().toInt()}°C/" +
                                    "${currentDay.value.minTemp.toFloat().toInt()}°C"
                        } else {
                            ""
                        }
                    },

                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )
                Text(
                    text = currentDay.value.condition,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        onClickSearch.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "img_search",
                            tint = Color.White
                        )
                    }
                    Text(
                        text = if (currentDay.value.maxTemp.isNotEmpty() && currentDay.value.minTemp.isNotEmpty()) {
                            "${currentDay.value.maxTemp.toFloat().toInt()}°C/" +
                                    "${currentDay.value.minTemp.toFloat().toInt()}°C"
                        } else {
                            ""
                        },
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )
                    IconButton(onClick = {
                        onClickSync.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.sync_icon),
                            contentDescription = "img_sync",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(daysList: MutableState<List<WeatherData>>, currentDay: MutableState<WeatherData>) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { tabPosition ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPosition
                    )
                )
            },
            backgroundColor = BlueLight,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                    text = {
                        Text(text = text, color = Color.White)
                    })
            }
        }
    }
    HorizontalPager(
        count = tabList.size,
        state = pagerState,
    ) { index ->
        val list = when (index) {
            0 -> getWeatherByHours(currentDay.value.hours)
            1 -> daysList.value
            else -> daysList.value
        }
        MainList(
            list = list,
            currentDay = currentDay
        )
    }
}

private fun getWeatherByHours(hours: List<Hour>): List<WeatherData> {
    if (hours.isEmpty()) return listOf()
    val list = ArrayList<WeatherData>()
    for (element in hours) {
        list.add(
            WeatherData(
                city = "",
                time = element.time,
                currentTemp = element.temp_c.toFloat().toInt().toString() + "°C",
                condition = element.condition.text,
                icon = element.condition.icon,
                "", "", listOf()
            )
        )
    }
    return list
}