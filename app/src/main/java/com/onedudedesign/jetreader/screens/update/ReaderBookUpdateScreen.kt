package com.onedudedesign.jetreader.screens.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.onedudedesign.jetreader.components.ReaderAppBar
import com.onedudedesign.jetreader.data.DataOrException
import com.onedudedesign.jetreader.model.MBook
import com.onedudedesign.jetreader.screens.home.ReaderHomeScreenViewModel

@Composable
fun ReaderBookUpdateScreen(
    navController: NavHostController, bookItemId: String,
    viewModel: ReaderHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Update Book",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean, Exception>>(
            initialValue = DataOrException(
                data = emptyList(),
                true,
                Exception("")
            )
        ) {
            value = viewModel.data.value
        }.value

        Surface(
            Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Log.d("INFO", "ReaderBookUpdateScreen: ${viewModel.data.value.data.toString()}")
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Text(text = viewModel.data.value.data?.get(0)?.title.toString())
                }
            }

        }

    }

}