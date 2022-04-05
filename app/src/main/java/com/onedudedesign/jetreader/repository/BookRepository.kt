package com.onedudedesign.jetreader.repository

import android.util.Log
import com.onedudedesign.jetreader.data.DataOrException
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item
import com.onedudedesign.jetreader.network.BooksApi
import javax.inject.Inject


class BookRepository @Inject constructor(private val api: BooksApi) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)

        } catch (exception: Exception) {
            Log.d("REPOSITORY", "getBooks: FAILED: ${exception.message.toString()}")
            Resource.Error(message = exception.message.toString())
        }
    }

    suspend fun getBookInfo(bookId: String): Resource<Item>{
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)
        }catch (exception: Exception){
            return Resource.Error(message = "An error occured ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}