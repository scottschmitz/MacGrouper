package com.sschmitz.macgrouper

abstract class ChildItem: Item() {
  override val itemCount = 1
  override val visibleCount = 1

  override fun getItem(position: Int): Item {
    return this
  }
}
