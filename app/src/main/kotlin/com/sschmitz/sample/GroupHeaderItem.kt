package com.sschmitz.sample

import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.sschmitz.macgrouper.HeaderItem

class GroupHeaderItem: HeaderItem() {

  @BindView(R.id.name) lateinit var name: TextView

  override fun getLayout() = R.layout.group_header_item

  override fun bind(view: View) {
    ButterKnife.bind(this, view)

    name.text = "This is a group header item"
  }
}
