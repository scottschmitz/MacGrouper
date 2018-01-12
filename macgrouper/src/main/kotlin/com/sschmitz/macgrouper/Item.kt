package com.sschmitz.macgrouper

import android.support.annotation.LayoutRes
import android.view.View

abstract class Item {
  abstract val visibleCount: Int
  abstract val itemCount: Int

  abstract fun getItem(position: Int): Item
  abstract fun bind(view: View)

  @LayoutRes
  abstract fun getLayout(): Int

  @Suppress("UNCHECKED_CAST")
  fun createViewHolder(view: View): ViewHolder {
    return ViewHolder(view)
  }
}
