package com.onedudedesign.jetreader.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item
import com.onedudedesign.jetreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderBookSearchViewModel @Inject constructor(private val repository: BookRepository)
    :ViewModel() {
        var list: List<Item> by mutableStateOf(listOf())
        var isloading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("Flutter")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            //  isloading = true removed as it is already true when the viewmodel is created
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)){
                    is Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isloading = false
                    }
                    is Resource.Error -> {
                        isloading = false
                        Log.e("TAG", "searchBooks: failed getting books", )
                    }
                    else -> {isloading=false}
                }

            }catch (exception: Exception){
                isloading=false
                Log.d("Network", "searchBooks: ${exception.message.toString()}")
            }
        }
    }
}