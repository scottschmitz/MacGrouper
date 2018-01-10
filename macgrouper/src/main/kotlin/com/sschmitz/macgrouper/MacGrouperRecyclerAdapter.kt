package com.sschmitz.macgrouper

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers

class MacGrouperRecyclerAdapter(
  @LayoutRes private val headerLayout: Int,
  @LayoutRes private val childLayout: Int
): RecyclerView.Adapter<ViewHolder>() {

  companion object {
    private const val VIEW_TYPE_GROUP = 1
    private const val VIEW_TYPE_CHILD = 2
  }

  var data = listOf<Item<ViewHolder>>(
    GroupItem(),
    GroupItem()
  )

  init {
    data.forEach { group ->
      if (group is GroupItem) {
        group.rangeRemovedSubject
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe { (sectionPosition, length) ->
            val groupPosition = getPositionForGroup(group)

            System.out.println("removing at ${groupPosition + sectionPosition} length $length")
            notifyItemRangeRemoved(groupPosition + sectionPosition, length)
          }

        group.rangeInsertedSubject
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe { (sectionPosition, length) ->
            val groupPosition = getPositionForGroup(group)
            System.out.println("inserting at ${groupPosition + sectionPosition} length $length")
            notifyItemRangeInserted(groupPosition + sectionPosition, length)
          }
      }
    }
  }

  override fun getItemCount(): Int {
    return data.sumBy { group ->
      group.visibleCount
    }
  }

  override fun getItemViewType(position: Int): Int {

    System.out.println("Getting for position: $position")

    var count = 0
    data.forEach { group ->
      if (position < count + group.visibleCount) {
        return if (position - count == 0) {
          System.out.println("---------Group")
          VIEW_TYPE_GROUP
        } else {
          System.out.println("---------Child")
          VIEW_TYPE_CHILD
        }
      }
      count += group.visibleCount
    }

    throw RuntimeException("Unknown View Type")
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return when(viewType) {
      VIEW_TYPE_GROUP -> {
        val view = inflater.inflate(headerLayout, parent, false)
        ViewHolder(view) //TODO: Correct View Holder
      }
      VIEW_TYPE_CHILD -> {
        val view = inflater.inflate(childLayout, parent, false)
        ViewHolder(view) //TODO: Correct View Holder
      }
      else -> throw RuntimeException("Attempting to create a ViewHolder for an unknown type")
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    var count = 0

    data.forEach { group ->
      System.out.print("HEADER ")

      if (group is GroupItem) {
        System.out.print(if (group.isExpanded) group.children.map { " CHILD " } else "")
      }
    }

    System.out.println("------")

    val item = getGroupAtPosition(position)
    holder.bind(item)
    holder.view.setOnClickListener {
      if (item is HeaderItem) {
        item.toggleExpand()
      }
    }
  }

  private fun getGroupAtPosition(position: Int): Item<ViewHolder> {
    var count = 0
    data.forEach { group ->
      if (position < count + group.visibleCount) {
        return group.getItem(position - count)
      }

      count += group.visibleCount
    }

    throw RuntimeException("Couldnt find that Group dog")
  }

  private fun getPositionForGroup(group: GroupItem<ViewHolder>): Int {
    System.out.println(data)

    var count = 0
    data.forEach {
      if (it == group) {
        return count
      }
      count += it.visibleCount
    }

    throw RuntimeException("Couldn't find that group dog")
  }
}
