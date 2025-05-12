package com.bussiness.seniorcareapp.utils

object ErrorMessage {
    const val EMAIL_ERROR  = "Please enter a valid email address/Phone number"
    const val PASSWORD_ERROR  = "Please enter your password"
    const val PASSWORD_PATTERN  = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$"
    const val PASSWORD_PATTERN_ERROR  = "Password must contain at least 1 uppercase, 1 lowercase, 1 number, 1 special character, and be 8+ characters"
    const val NAME_ERROR = "Please enter your name"
    const val PHONE_ERROR = "Please enter your phone number"
    const val PASSWORD_MISMATCH_ERROR = "Confirm password does not match"
    const val FEEDBACK_ERROR = "Please enter your feedback"
    const val CHECKBOX_ERROR = "Please accept terms and condition"
    const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
}