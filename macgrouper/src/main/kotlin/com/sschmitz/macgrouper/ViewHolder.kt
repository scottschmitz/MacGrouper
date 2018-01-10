package com.sschmitz.macgrouper

import android.support.v7.widget.RecyclerView
import android.view.View

// TODO: ABSTRACT
class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
  fun bind(item: Item<ViewHolder>){}
}
