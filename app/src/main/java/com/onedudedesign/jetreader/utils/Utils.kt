package com.onedudedesign.jetreader.utils

import android.util.Log
import com.onedudedesign.jetreader.data.Resource
import com.onedudedesign.jetreader.model.Item

class Utils {
    fun getImageDetailContent(book: Resource<Item>): String {
        var imageUrl = ""
        //placed inside try due to a few records not having the image links at all causing nullpointer reference
        try {
            imageUrl = book.data?.volumeInfo?.imageLinks?.smallThumbnail!!
        } catch (e: Exception) {
            Log.d("IMAGEEXC", "BookRow: ${e.message.toString()}")
            imageUrl =
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
        }
        return imageUrl
    }
}