package com.example.e_commerce.components

import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

fun resetPassword(email: String, context: android.content.Context) {
    val auth = FirebaseAuth.getInstance()

    auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Password reset email sent", Toast.LENGTH_SHORT).show()
            } else {
                val errorMessage = task.exception?.message ?: "Password reset failed"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
}
