package com.shop.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.shop.utils.GenericClickListener

class TwoGenericsRecyclerAdapter<T : Any, A : Any>(
    private val dataList: List<T>,
    private val dataSecondList: List<A>,
    @LayoutRes val layoutID: Int,
    private val bindingInterface: TwoGenericsRecyclerBindingInterface<T, A>,
    private var clickListener: GenericClickListener<T>? = null
) : RecyclerView.Adapter<TwoGenericsRecyclerAdapter.ViewHolder>() {

    interface TwoGenericsRecyclerBindingInterface<T : Any, A : Any> {
        fun bindData(
            item: T,
            secondItem: A,
            view: View,
            clickListener: GenericClickListener<T>?,
            position: Int
        )
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any, A : Any> bind(
            item: T,
            secondItem: A,
            bindingInterface: TwoGenericsRecyclerBindingInterface<T, A>,
            clickListener: GenericClickListener<T>?,
            position: Int,
        ) {
            bindingInterface.bindData(item, secondItem, view, clickListener, position)

            clickListener?.let {
                view.setOnClickListener {
                    clickListener.onClick(position, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        val secondItem = dataSecondList[position]
        holder.bind(item, secondItem, bindingInterface, clickListener, position)
    }

    fun setOnClickListener(genericClickListener: GenericClickListener<T>) {
        this.clickListener = genericClickListener
    }
}