package com.onedudedesign.jetreader.screens.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.onedudedesign.jetreader.components.ReaderAppBar
import com.onedudedesign.jetreader.components.RoundedButton
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item
import com.onedudedesign.jetreader.model.MBook
import com.onedudedesign.jetreader.navigation.ReaderScreens
import com.onedudedesign.jetreader.utils.Utils

@Composable
fun ReaderBookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: ReaderBookDetailsViewModel = hiltViewModel()
) {
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

                if (bookInfo.data == null) {
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


                } else { //Detail content
                    DetailContent(bookInfo, navController)
                }


            }

        }
    }
}

@Composable
fun DetailContent(book: Resource<Item>, navController: NavController) {
    val title = book.data?.volumeInfo?.title
    val authors = book.data?.volumeInfo?.authors.toString()
    val pageCount = book.data?.volumeInfo?.pageCount.toString()
    val categories = book.data?.volumeInfo?.categories.toString()
    val publishedDate = book.data?.volumeInfo?.publishedDate
    val bookDescription = book.data?.volumeInfo?.description
    val rating = book.data?.volumeInfo?.ratingsCount?.toDouble()
    val googleBookId = book.data?.id

    val context = LocalContext.current
    Surface(
        Modifier
            .fillMaxSize()
            .padding(2.dp)
    ) {


        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Book image at top from util function
            Image(
                painter = rememberImagePainter(data = Utils().getImageDetailContent(book)),
                contentDescription = "Book Image",
                modifier = Modifier
                    .width(125.dp)
                    .height(125.dp)
                    .clip(shape = CircleShape)

            )
            Text(
                text = title!!,
                Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4
            )
            Text(
                text = "Authors: $authors",
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "PageCount: $pageCount",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Categories: $categories",
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = "Published: $publishedDate",
                style = MaterialTheme.typography.subtitle1
            )
            Box(
                Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(width = 1.dp, Color.Black)
                    .verticalScroll(rememberScrollState())
            ) {
                //clean out the HTML Tags
                val cleanDescription = HtmlCompat.fromHtml(
                    bookDescription!!,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString()
                Text(text = cleanDescription)

            }

            Row(Modifier.padding(top = 20.dp)) {
                RoundedButton(label = "Save") {

                    Toast.makeText(context, "Save the Book to Firestore", Toast.LENGTH_LONG).show()
                    val saveBook = MBook(
                        title = title,
                        authors = authors.toString(),
                        notes = "",
                        photoUrl = Utils().getImageDetailContent(book),
                        categories = categories,
                        publishedDate = publishedDate,
                        rating = rating,
                        description = bookDescription,
                        pageCount = pageCount,
                        googleBookId = googleBookId,
                        userId = FirebaseAuth.getInstance().currentUser?.uid.toString()


                    )
                    saveToFirebase(book = saveBook, navController = navController)
                }
                Spacer(modifier = Modifier.width(20.dp))
                RoundedButton(label = "Cancel") {
                    navController.popBackStack()
                }

            }

        }
    }
}


fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener {
                val docId = it.id
                //adding the document id ofmthe created book document to the document itself in the id field
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            navController.popBackStack()
                        }
                    }.addOnFailureListener {
                        Log.w("Error", "SaveToFirebase: Error updating doc", it)
                    }
            }
    } else {

    }
}


