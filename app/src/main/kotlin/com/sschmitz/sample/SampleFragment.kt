package com.sschmitz.sample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.sschmitz.macgrouper.GroupItem
import com.sschmitz.macgrouper.MacGrouperRecyclerAdapter

class SampleFragment: Fragment() {

  @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

  private var unbinder: Unbinder? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_sample, container, false)
    unbinder = ButterKnife.bind(this, view)

    recyclerView.layoutManager = LinearLayoutManager(context)

    val group = GroupItem(
      GroupHeaderItem(),
      listOf(GroupChildItem(), GroupChildItem())
    )

    val group2 = GroupItem(
      GroupHeaderItem(),
      listOf(GroupChildItem(), GroupChildItem())
    )

    val group3 = GroupItem(
      GroupHeaderItem(),
      listOf(GroupChildItem(), GroupChildItem())
    )

    recyclerView.adapter = MacGrouperRecyclerAdapter(listOf(group, group2, group3))

    return view
  }

  override fun onDestroyView() {
    super.onDestroyView()
    unbinder?.unbind()
  }
}
