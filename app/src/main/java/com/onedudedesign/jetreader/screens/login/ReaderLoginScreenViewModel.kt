package com.onedudedesign.jetreader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.onedudedesign.jetreader.model.MUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReaderLoginScreenViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.d("FB", "signInWithEmailAndPassword: ${it.result.toString()}")
                            //TODO("take them to the home screen")
                            home()
                        } else {
                            Log.d("FB", "signInWithEmailAndPassword: ${it.result.toString()} ")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("LOGIN", "signInWithEmailAndPassword: FAILED ${it.message}")
                    }

            } catch (ex: Exception) {
                Log.d("FB", "signInWithEmailAndPassword: ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(
        email: String, password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val displayName = it.result.user?.email?.split("@")?.get(0)
                        CreateUser(displayName)
                        home()
                    } else {
                        Log.d("FB", "createUserWithEmailAndPassword: ${it.result}")
                    }
                    _loading.value = false
                }
        }

    }

    private fun CreateUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = MUser(id = null,
            userId = userId.toString(),
            displayName = displayName.toString(),
            avatarUrl = "",
            quote = "",
            profession = ""
        ).toMap()


        FirebaseFirestore.getInstance().collection("users")
            .add(user)
    }


}