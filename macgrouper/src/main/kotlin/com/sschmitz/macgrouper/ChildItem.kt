package com.sschmitz.macgrouper

import android.support.v7.widget.RecyclerView

class ChildItem<out VH: RecyclerView.ViewHolder>: Item<VH>() {
  override val itemCount = 1
  override val visibleCount = 1

  override fun getItem(position: Int): Item<VH> {
    return this
  }
}
