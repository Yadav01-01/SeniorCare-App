package com.bussiness.seniorcareapp.data.model

data class FAQItem(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)
