package com.example.littlelivesassignment.utils.ext

import android.view.View

fun View.measureBy(width: Int, widthSpec: Int, height: Int, heightSpec: Int) {
    measure(
        View.MeasureSpec.makeMeasureSpec(width, widthSpec),
        View.MeasureSpec.makeMeasureSpec(height, heightSpec)
    )
}

fun View.layoutByTopLeft(t: Int, l: Int) {
    layout(l, t, l + measuredWidth, t + measuredHeight)
}

fun View.layoutByTopRight(t: Int, r: Int) {
    layout(r - measuredWidth, t, r, t + measuredHeight)
}

fun View.layoutByBottomLeft(b: Int, l: Int) {
    layout(l, b - measuredHeight, l + measuredWidth, b)
}

fun View.layoutByBottomRight(b: Int, r: Int) {
    layout(r - measuredWidth, b - measuredHeight, r, b)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}