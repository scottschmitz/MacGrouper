package com.sschmitz.macgrouper

import android.support.v7.widget.RecyclerView

abstract class Item<out VH: RecyclerView.ViewHolder> {
  abstract val visibleCount: Int
  abstract val itemCount: Int

  abstract fun getItem(position: Int): Item<VH>
}
