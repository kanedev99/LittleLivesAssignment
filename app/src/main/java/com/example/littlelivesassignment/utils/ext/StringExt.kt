package com.example.littlelivesassignment.utils.ext

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): String {
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val outputDateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)
    val date = inputDateFormat.parse(this) ?: return ""
    return outputDateFormat.format(date)
}

fun String.boldText(text: String): Spannable {
    val styledString = SpannableString(this)
    val textStart = this.indexOf(text)
    val textEnd = textStart + text.length
    styledString.setSpan(StyleSpan(Typeface.BOLD), textStart, textEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return styledString
}

fun String.boldText(text1: String, text2: String): Spannable {
    val styledString = SpannableString(this)

    val text1Start = this.indexOf(text1)
    val text1End = text1Start + text1.length
    styledString.setSpan(StyleSpan(Typeface.BOLD), text1Start, text1End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    val text2Start = this.indexOf(text2)
    val text2End = text2Start + text1.length
    styledString.setSpan(StyleSpan(Typeface.BOLD), text2Start, text2End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return styledString
}