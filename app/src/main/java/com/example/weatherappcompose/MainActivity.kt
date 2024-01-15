package com.example.weatherappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weatherappcompose.ui.DialogSearch
import com.example.weatherappcompose.ui.MainCard
import com.example.weatherappcompose.ui.TabLayout
import com.example.weatherappcompose.viewModels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.getWeatherResponse("Volgograd")
            if (viewModel.dialogState.value) {
                DialogSearch(viewModel.dialogState, onSubmit = {
                    viewModel.getWeatherResponse(it)
                })
            }
            Image(
                painter = painterResource(id = R.drawable.image_sky),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.75f),
                contentScale = ContentScale.Crop
            )
            Column {
                MainCard(
                    viewModel.currentDay,
                    onClickSync = {
                        viewModel.getWeatherResponse("Volgograd")
                    },
                    onClickSearch = {
                        viewModel.dialogState.value = true
                    })
                TabLayout(viewModel.daysList, viewModel.currentDay)
            }
        }
    }
}
