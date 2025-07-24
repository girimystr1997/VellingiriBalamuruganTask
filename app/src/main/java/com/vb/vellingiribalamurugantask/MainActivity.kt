package com.vb.vellingiribalamurugantask

import android.os.Bundle
import com.vb.vellingiribalamurugantask.adapter.UserHoldingItemAdapter
import com.vb.vellingiribalamurugantask.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var userHoldingItemAdapter: UserHoldingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userHoldingItemAdapter = UserHoldingItemAdapter(this)
    }

    override fun getLayout() = R.layout.activity_main
}