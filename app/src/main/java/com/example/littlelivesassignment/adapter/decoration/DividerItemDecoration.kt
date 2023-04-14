package com.example.littlelivesassignment.adapter.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.littlelivesassignment.R
import com.example.littlelivesassignment.adapter.EventListAdapter

class DividerItemDecoration(context: Context, dividerDrawable: Drawable) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable = dividerDrawable
    private val contentPadding = context.resources.getDimensionPixelSize(R.dimen.event_item_padding)
    private val dividerMarginH = context.resources.getDimensionPixelSize(R.dimen.divider_margin_horizontal)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter  = parent.adapter ?: return

        for (i in 0 until parent.childCount - 1) {
            val child = parent.getChildAt(i)
            child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + contentPadding
            val bottom = top + mDivider.intrinsicHeight

            val position = parent.getChildAdapterPosition(child)

            if (adapter.getItemViewType(position) == EventListAdapter.ITEM_TYPE) {
                mDivider.setBounds(0, top, parent.width, bottom)
                mDivider.draw(c)
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter  = parent.adapter ?: return
        val position = parent.getChildAdapterPosition(view)
        val viewType = adapter.getItemViewType(position)

        if (viewType == EventListAdapter.TITLE_SECTION_TYPE) {
            outRect.left    = 0
            outRect.right   = 0
            outRect.top     = 0
        } else {
            outRect.top     = contentPadding
            outRect.left    = contentPadding
            outRect.right   = contentPadding
        }

        outRect.bottom  = contentPadding
    }
}
