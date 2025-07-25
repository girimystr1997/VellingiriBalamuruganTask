package com.vb.vellingiribalamurugantask.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import com.vb.vellingiribalamurugantask.BaseRecyclerViewAdapter
import com.vb.vellingiribalamurugantask.R
import com.vb.vellingiribalamurugantask.databinding.UserHoldingItemBinding
import com.vb.vellingiribalamurugantask.utils.formatAmount
import com.vb.vellingiribalamurugantask.model.UserHolding

class UserHoldingItemAdapter(context: Context) :
    BaseRecyclerViewAdapter<UserHoldingItemBinding>(context) {

    var userHolding: List<UserHolding> = emptyList()

    override fun setViewHolder(
        holder: MyViewHolder,
        holderBinding: UserHoldingItemBinding,
        position: Int
    ) {
        with(userHolding[position]){
            holderBinding.symbol.text = symbol
            holderBinding.ltpValue.text = ltp.formatAmount()
            holderBinding.qtyValue.text = quantity.toString()
            holderBinding.totalPnl.apply {
                val tpl = ((ltp * quantity) - (avgPrice * quantity))
                val colorRes = if (tpl < 0) R.color.red else R.color.green
                setTextColor(ContextCompat.getColor(context, colorRes))
                text = tpl.formatAmount()
            }
        }
    }


    fun setData(holdings: List<UserHolding>) {
        userHolding = holdings
        notifyDataSetChanged()
    }

    override fun getLayout() = R.layout.user_holding_item

    override fun getCount() = userHolding.size
}