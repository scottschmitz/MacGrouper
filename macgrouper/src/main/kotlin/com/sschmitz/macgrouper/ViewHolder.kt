package com.sschmitz.macgrouper

import android.support.v7.widget.RecyclerView
import android.view.View

open class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
  private var item: Item? = null
}
