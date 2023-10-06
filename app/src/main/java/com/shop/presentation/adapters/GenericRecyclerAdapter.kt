package com.shop.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class GenericRecyclerAdapter<T : Any>(
    private val dataList: List<T>, @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericRecyclerBindingInterface<T>
) :
    RecyclerView.Adapter<GenericRecyclerAdapter.ViewHolder>() {

    interface GenericRecyclerBindingInterface<T> {
        fun bindData(item: T, view: View)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterface<T>
        ) = bindingInterface.bindData(item, view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item, bindingInterface)
    }
}