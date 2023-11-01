package com.shop.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shop.utils.GenericClickListener

class GenericRecyclerAdapter<T : Any>(
    @LayoutRes val layoutID: Int,
    private val bindingInterface: GenericRecyclerBindingInterface<T>,
    private var clickListener: GenericClickListener<T>? = null
) :
    ListAdapter<T, GenericRecyclerAdapter.ViewHolder>(GenericDiffUtilCallback()) {

    interface GenericRecyclerBindingInterface<T> {
        fun bindData(
            item: T, view: View, clickListener: GenericClickListener<T>?,
            position: Int
        )
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any> bind(
            item: T,
            bindingInterface: GenericRecyclerBindingInterface<T>,
            clickListener: GenericClickListener<T>?,
            position: Int,
        ) {
            bindingInterface.bindData(item, view, clickListener, position)

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), bindingInterface, clickListener, position)
    }

    class GenericDiffUtilCallback<T : Any> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }

    fun setOnClickListener(genericClickListener: GenericClickListener<T>) {
        this.clickListener = genericClickListener
    }
}
