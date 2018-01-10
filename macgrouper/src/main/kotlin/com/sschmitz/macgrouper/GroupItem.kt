package com.sschmitz.macgrouper

import io.reactivex.subjects.BehaviorSubject

class GroupItem<out VH: ViewHolder>: Item<VH>() {
  val header: HeaderItem<VH> = HeaderItem()
  val children: List<Item<VH>> = listOf(ChildItem(), ChildItem())

  val rangeInsertedSubject = BehaviorSubject.create<Pair<Int, Int>>()
  val rangeRemovedSubject = BehaviorSubject.create<Pair<Int, Int>>()

  var isExpanded = true

  init {
    header.expandSubject
      .subscribe { isExpanded ->
        if (isExpanded) {
          expand()
        } else {
          collapse()
        }
      }
  }

  override val itemCount: Int
    get() = children.size + 1

  override val visibleCount: Int
    get() { return if (isExpanded) { children.size + 1 } else 1 }


  override fun getItem(position: Int): Item<VH> {
    System.out.println("Getting position $position for a list of items ${children.size}")
    return when (position) {
      0 -> header
      else -> children[position - 1]
    }
  }

  fun expand() {
    isExpanded = true
    rangeInsertedSubject.onNext(Pair(1, children.size))
  }

  fun collapse() {
    isExpanded = false
    rangeRemovedSubject.onNext(Pair(1, children.size))
  }
}
