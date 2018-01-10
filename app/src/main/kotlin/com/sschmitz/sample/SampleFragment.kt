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
import com.sschmitz.macgrouper.MacGrouperRecyclerAdapter

class SampleFragment: Fragment() {

  @BindView(R.id.recyclerView) lateinit var recyclerView: RecyclerView

  private var unbinder: Unbinder? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_sample, container, false)
    unbinder = ButterKnife.bind(this, view)

    recyclerView.layoutManager = LinearLayoutManager(context)
    recyclerView.adapter = MacGrouperRecyclerAdapter(R.layout.group_header_item, R.layout.group_child_item)

    return view
  }

  override fun onDestroyView() {
    super.onDestroyView()
    unbinder?.unbind()
  }
}
