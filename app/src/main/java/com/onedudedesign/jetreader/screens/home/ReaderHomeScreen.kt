package com.onedudedesign.jetreader.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.onedudedesign.jetreader.components.FabContent
import com.onedudedesign.jetreader.components.ListCard
import com.onedudedesign.jetreader.components.ReaderAppBar
import com.onedudedesign.jetreader.components.TitleSection
import com.onedudedesign.jetreader.model.MBook
import com.onedudedesign.jetreader.navigation.ReaderScreens

@Composable
fun ReaderHomeScreen(
    navController: NavController,
    viewModel: ReaderHomeScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Welcome Home", navController = navController)
    }, floatingActionButton = {
        FabContent {
            navController.navigate(ReaderScreens.ReaderBookSearchScreen.name)
        }
    }) {
        //content
        Surface(modifier = Modifier.fillMaxSize()) {
            ReaderHomeContent(navController, viewModel)
        }
    }
}

@Composable
fun ReaderHomeContent(navController: NavController, viewModel: ReaderHomeScreenViewModel) {
    val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0) else "N/A"
    val currentUser = FirebaseAuth.getInstance().currentUser
    var listOfBooks = emptyList<MBook>()

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter {
            it.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "ReaderHomeContent: ${listOfBooks.toString()}")
    }
//    val listOfBooks = listOf(
//        MBook("asd,.hklfgasfg", "asdfgasfg", "vcnju", "asfgasfg"),
//        MBook("asdfgasfg", "dfghk", "asfgasfg", "asfgasfg"),
//        MBook("dsfhj", "asdfgasfg", "cvb", "asfgasfg"),
//        MBook("sartasfg", "asdfgasfg", "asfgasfg", "asfgasfg"),
//        MBook("asdfgasfg", "gydghd", "dnsdg", "asfgasfg")
//    )

    Column(
        modifier = Modifier
            .padding(2.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.align(Alignment.Start)) {
            TitleSection(label = "Your reading\n activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column(modifier = Modifier.clickable {
                navController.navigate(ReaderScreens.ReaderStatsScreen.name)
            }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(45.dp),
                    tint = MaterialTheme.colors.secondaryVariant
                )
                Text(
                    text = currentUserName!!,
                    modifier = Modifier
                        .padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReadingRightNowArea(books = listOfBooks, navController = navController)

        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)

    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        Log.d("CLICK", "BookListArea: $it")
        navController.navigate(ReaderScreens.ReaderBookUpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: ReaderHomeScreenViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .horizontalScroll(scrollState)

    ) {
        if (viewModel.data.value.loading == true){
            LinearProgressIndicator()
        } else {
            if (listOfBooks.isNullOrEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No Books Found. Add a Book",
                    style = TextStyle(Color.Red.copy(alpha = 0.4f),
                    fontWeight = FontWeight.Bold)
                    )
                }
            } else {
                for (book in listOfBooks) {
                    ListCard(book = book) {
                        onCardPressed(book.googleBookId.toString())
                    }
                }
            }

        }

    }
}


@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    val readingNowList = books.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(readingNowList) {
        navController.navigate(ReaderScreens.ReaderBookUpdateScreen.name + "/$it")
    }
}








