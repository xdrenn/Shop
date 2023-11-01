package com.shop.utils

interface GenericClickListener<T> {
    fun onClick(position: Int, data: T)
}