package com.onedudedesign.jetreader.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedudedesign.jetreader.data.DataOrException
import com.onedudedesign.jetreader.model.MBook
import com.onedudedesign.jetreader.repository.BookRepository
import com.onedudedesign.jetreader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderHomeScreenViewModel @Inject constructor(
    private val repository: FireRepository
) : ViewModel() {
    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>>
    = mutableStateOf(
        DataOrException(listOf(), true, Exception(""))
    )

    init {
        getAllBooksFromDatabase()
    }

    private fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
        Log.d("DATA", "getAllBooksFromDatabase: ${data.value.data?.toList().toString()}")
    }
}