package com.campino.expenses.view

import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.forEachIndexed
import androidx.recyclerview.widget.RecyclerView
import com.campino.expenses.R
import com.google.android.material.appbar.MaterialToolbar


abstract class BaseItemCardAdapter<E, H : ItemHolder<E>> : RecyclerView.Adapter<H>() {

    private var onClickItemListener: ((E) -> Unit)? = null
    private var onClickActionListener: ((MenuItem?, E) -> Unit)? = null
    protected val list = mutableListOf<E>()

    fun <L : List<E>> update(listContainers: L) {
        list.clear()
        list.addAll(listContainers)
        notifyDataSetChanged()
    }

    fun setOnClickItem(listener: (E) -> Unit) {
        onClickItemListener = listener
    }

    fun setOnClickAction(listener: ((MenuItem?, E) -> Unit)?) {
        onClickActionListener = listener
    }

    private fun onClickItem(position: Int) {
        onClickItemListener?.invoke(list[position])
    }

    private fun onClickItemMore(item: MenuItem?, position: Int): Boolean {
        onClickActionListener?.invoke(item, list[position])
        return true
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H

    override fun onBindViewHolder(holder: H, position: Int) {
        holder.bindItem(list, position, ::onClickItem, ::onClickItemMore)
    }

}

abstract class ItemHolder<E>(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bindItem(
        list: List<E>,
        position: Int,
        onClickListener: ((position: Int) -> Unit),
        onMoreListener: (view: MenuItem?, position: Int) -> Boolean
    )

    fun setToolbarTintIcons(
        toolbar: MaterialToolbar,
        @ColorRes colorRes: Int
    ) {
        toolbar.menu.forEachIndexed { index, item ->
            item.icon.setTint(
                ContextCompat
                    .getColor(view.context, colorRes)
            )
        }
        val itemMenu = toolbar.menu.findItem(R.id.overflow) ?: return
        val menuOverflow = itemMenu.subMenu
        menuOverflow.forEachIndexed { index, item ->
            item.icon.setTint(
                ContextCompat
                    .getColor(view.context, colorRes)
            )
        }
    }
}