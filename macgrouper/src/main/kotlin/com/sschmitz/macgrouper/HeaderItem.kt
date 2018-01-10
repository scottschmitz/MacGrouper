package com.sschmitz.macgrouper

import android.support.v7.widget.RecyclerView
import io.reactivex.subjects.BehaviorSubject

class HeaderItem<out VH: RecyclerView.ViewHolder>: Item<VH>() {
  override val itemCount = 1
  override val visibleCount = 1

  var expanded = true
  set(value) {
    field = value
    expandSubject.onNext(value)
  }
  val expandSubject = BehaviorSubject.create<Boolean>()

  override fun getItem(position: Int): Item<VH> {
    return this
  }

  fun toggleExpand() {
    expanded = !expanded
  }
}
