package com.sschmitz.macgrouper

import io.reactivex.subjects.BehaviorSubject

abstract class HeaderItem: Item() {
  override val itemCount = 1
  override val visibleCount = 1

  var expanded = true
  set(value) {
    field = value
    expandSubject.onNext(value)
  }
  val expandSubject = BehaviorSubject.create<Boolean>()

  override fun getItem(position: Int): Item {
    return this
  }

  fun toggleExpand() {
    expanded = !expanded
  }
}
