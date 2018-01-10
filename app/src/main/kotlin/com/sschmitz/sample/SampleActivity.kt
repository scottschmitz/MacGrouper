package com.sschmitz.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import butterknife.ButterKnife

class SampleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sample)

    ButterKnife.bind(this)

    if (savedInstanceState == null) {
      val mainFragment = SampleFragment()
      mainFragment.arguments = intent.extras
      supportFragmentManager.beginTransaction()
        .add(R.id.content, mainFragment)
        .commit()
    }
  }
}
