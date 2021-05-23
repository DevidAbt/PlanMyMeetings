package com.example.planmymeetings.screens.register

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordAgain = MutableLiveData<String>()

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

    private val _eventFinishActivity = MutableLiveData<Boolean>()
    val eventFinishActivity: LiveData<Boolean>
        get() = _eventFinishActivity

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register() {
        if (username.value.isNullOrEmpty() || email.value.isNullOrEmpty() ||
            password.value.isNullOrEmpty() || passwordAgain.value.isNullOrEmpty()
        ) {
            _toastMessage.value = "You must fill all fields."
            return
        }

        if (!password.value.equals(passwordAgain.value)) {
            _toastMessage.value = "The passwords are not the same."
            return
        }

        mAuth.createUserWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val successText = "User created successfully."
                    _toastMessage.value = successText
                    _eventFinishActivity.value = true
                } else {
                    val errorText = "User creation failed (${it.exception?.message})."
                    _toastMessage.value = errorText
                }
            }
    }

    fun resetToastMessage() {
        _toastMessage.value = null;
    }
}