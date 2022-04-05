package com.onedudedesign.jetreader.screens.details

import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.onedudedesign.jetreader.components.ReaderAppBar
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item
import com.onedudedesign.jetreader.navigation.ReaderScreens
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun ReaderBookDetailsScreen(navController: NavController, bookId: String, viewModel: ReaderBookDetailsViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(ReaderScreens.ReaderBookSearchScreen.name)
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                //this produce state bypasses all the viewmodel scope stuff like what is in the readerSearch viewModel
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId = bookId)
                }.value

                if (bookInfo.data == null){
                    Row(modifier = Modifier.fillMaxWidth()) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .padding(10.dp)

                        )
                        Text(
                            text = "Loading...",
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }


                } else {
                    Text(text = "Book Description")
                    Text(text = "Book ID: ${bookInfo.data.id}")
                }


            }

        }
    }
}