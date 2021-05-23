package com.example.planmymeetings.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()

    val password = MutableLiveData<String>()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login() {
        if (email.value.isNullOrEmpty() || password.value.isNullOrEmpty()) {
            _toastMessage.value = "You must fill all fields."
            return
        }

        mAuth.signInWithEmailAndPassword(email.value!!, password.value!!).addOnCompleteListener {
            if (it.isSuccessful) {
                val successText = "Logged in successfully."
                _toastMessage.value = successText
            } else {
                val errorText = "Login failed (${it.exception?.message})."
                _toastMessage.value = errorText
            }
        }
    }

    fun resetToastMessage() {
        _toastMessage.value = null;
    }
}