package com.vb.vellingiribalamurugantask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T : ViewDataBinding>(private var context: Context) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(var dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val dataBinding:T = DataBindingUtil.inflate(LayoutInflater.from(context),getLayout(),parent,false)
        return MyViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        setViewHolder(holder,holder.dataBinding as T,position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    abstract fun setViewHolder(holder: MyViewHolder,holderBinding: T,position: Int)
    override fun getItemCount() = getCount()
    abstract fun getLayout():Int
    abstract fun getCount():Int
}