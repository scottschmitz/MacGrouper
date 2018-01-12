package com.sschmitz.macgrouper

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers

class MacGrouperRecyclerAdapter(var data: List<Item>): RecyclerView.Adapter<ViewHolder>() {

  companion object {
    private const val VIEW_TYPE_GROUP = 1
    private const val VIEW_TYPE_CHILD = 2
  }

  private var lastItemForViewTypeLookup: Item? = null

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
    val group = getGroupAtPosition(position)
    lastItemForViewTypeLookup = group
    return group.getLayout()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val item = getItemForViewType(viewType)
    val itemView = inflater.inflate(viewType, parent, false)
    return item.createViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getGroupAtPosition(position)
    item.bind(holder.view)
    holder.view.setOnClickListener {
      if (item is HeaderItem) {
        item.toggleExpand()
      }
    }
  }

  private fun getGroupAtPosition(position: Int): Item {
    var count = 0
    data.forEach { group ->
      if (position < count + group.visibleCount) {
        return group.getItem(position - count)
      }
      count += group.visibleCount
    }
    throw RuntimeException("Couldn't find that Group dog")
  }

  private fun getPositionForGroup(group: GroupItem): Int {
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

  /**
   * This idea was copied from Epoxy. :wave: Bright idea guys!
   *
   *
   * Find the model that has the given view type so we can create a viewholder for that model.
   *
   *
   * To make this efficient, we rely on the RecyclerView implementation detail that [ ][GroupAdapter.getItemViewType] is called immediately before [ ][GroupAdapter.onCreateViewHolder]. We cache the last model
   * that had its view type looked up, and unless that implementation changes we expect to have a
   * very fast lookup for the correct model.
   *
   *
   * To be safe, we fallback to searching through all models for a view type match. This is slow and
   * shouldn't be needed, but is a guard against RecyclerView behavior changing.
   */
  private fun getItemForViewType(@LayoutRes layoutResId: Int): Item {
    val lastItem = lastItemForViewTypeLookup
    if (lastItem != null && lastItem.getLayout() == layoutResId) {
      // We expect this to be a hit 100% of the time
      return lastItem
    }

    // To be extra safe in case RecyclerView implementation details change...
    for (i in 0 until itemCount) {
      val item = getGroupAtPosition(i)
      if (item.getLayout() == layoutResId) {
        return item
      }
    }

    throw IllegalStateException("Could not find model for view type: " + layoutResId)
  }
}
