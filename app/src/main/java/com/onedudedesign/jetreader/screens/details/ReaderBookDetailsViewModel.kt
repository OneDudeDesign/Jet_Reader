package com.onedudedesign.jetreader.screens.details

import androidx.lifecycle.ViewModel
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item
import com.onedudedesign.jetreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderBookDetailsViewModel @Inject constructor(private val repository: BookRepository)
    : ViewModel(){

        suspend fun getBookInfo(bookId: String): Resource<Item> {
            return repository.getBookInfo(bookId = bookId)
        }
}