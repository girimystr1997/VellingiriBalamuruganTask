package com.vb.vellingiribalamurugantask.adapter

import android.content.Context
import com.vb.vellingiribalamurugantask.BaseRecyclerViewAdapter
import com.vb.vellingiribalamurugantask.R
import com.vb.vellingiribalamurugantask.databinding.UserHoldingItemBinding

class UserHoldingItemAdapter(var context: Context): BaseRecyclerViewAdapter<UserHoldingItemBinding>(context) {
    override fun setViewHolder(
        holder: MyViewHolder,
        holderBinding: UserHoldingItemBinding,
        position: Int
    ) {

    }

    override fun getLayout() = R.layout.user_holding_item

    override fun getCount() = 2
}