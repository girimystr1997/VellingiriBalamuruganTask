package com.vb.vellingiribalamurugantask

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.vb.vellingiribalamurugantask.adapter.UserHoldingItemAdapter
import com.vb.vellingiribalamurugantask.databinding.ActivityMainBinding
import com.vb.vellingiribalamurugantask.utils.formatAmount
import com.vb.vellingiribalamurugantask.utils.showHideAnimation
import com.vb.vellingiribalamurugantask.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    lateinit var userHoldingItemAdapter: UserHoldingItemAdapter

    val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userHoldingItemAdapter = UserHoldingItemAdapter(this)
        dataBinding.recyclerView.adapter = userHoldingItemAdapter


        lifecycleScope.launch {
            mainViewModel.userHoldingData.collect {
                mainViewModel.showLoader.emit(it.userHolding.isNotEmpty())
                userHoldingItemAdapter.setData(it.userHolding, it.totalPl)
                with(dataBinding) {
                    currentValue.text = it.currentValue.formatAmount()
                    totalInvestment.text = it.totalInvestment.formatAmount()
                    todayPl.apply {
                        val colorRes = if (it.todayPl < 0) R.color.red else R.color.green
                        setTextColor(ContextCompat.getColor(context, colorRes))
                        text = it.todayPl.formatAmount()
                    }
                    totalPl.apply {
                        val colorRes = if (it.totalPl < 0) R.color.red else R.color.green
                        setTextColor(ContextCompat.getColor(context, colorRes))
                        text = it.totalPl.formatAmount()
                    }
                }
            }
        }
        dataBinding.pl.setOnClickListener {
            dataBinding.constraintLayout3.showHideAnimation {
                if (it) {
                    dataBinding.pl.setCompoundDrawablesWithIntrinsicBounds(
                        null, null,
                        ContextCompat.getDrawable(this, R.drawable.arrow_down), null
                    )
                } else {
                    dataBinding.pl.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        ContextCompat.getDrawable(this, R.drawable.arrow_up),
                        null
                    )
                }
            }

        }

        lifecycleScope.launch {
            mainViewModel.showLoader.collect {
                dataBinding.progressBar.isVisible = !it
            }
        }
    }

    override fun getLayout() = R.layout.activity_main
}