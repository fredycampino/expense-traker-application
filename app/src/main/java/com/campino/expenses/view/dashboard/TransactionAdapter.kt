package com.campino.expenses.view.dashboard

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.campino.expenses.R
import com.campino.expenses.domine.BalanceEntity
import com.campino.expenses.domine.ExpensesEntity
import com.campino.expenses.domine.IncomeEntity
import com.campino.expenses.domine.TransactionEntity
import com.campino.expenses.view.*
import com.google.android.material.appbar.MaterialToolbar

class TransactionAdapter : BaseItemCardAdapter<TransactionEntity, TransactionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        return TransactionHolder(
            LayoutInflater
                .from(parent.context).inflate(R.layout.item_transaction, parent, false)
        )
    }

}

class TransactionHolder(view: View) : ItemHolder<TransactionEntity>(view) {
    private val categoryText: TextView = view.findViewById(R.id.itemTitle)
    private val date: TextView = view.findViewById(R.id.itemDate)
    private val value: TextView = view.findViewById(R.id.itemValue)
    private val toolbar: MaterialToolbar = view.findViewById(R.id.itemToolbar)
    private val root: View = view.findViewById(R.id.itemRoot)
    private val spacer: View = view.findViewById(R.id.itemSpacer)

    init {
        setToolbarTintIcons(toolbar, R.color.colorIcon)
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    override fun bindItem(
        list: List<TransactionEntity>,
        position: Int,
        onClickListener: (position: Int) -> Unit,
        onMoreListener: (view: MenuItem?, position: Int) -> Boolean
    ) {
        val item = list[position]
        val next = if (position + 1 < list.size) list[position + 1] else null

        date.visibility = View.VISIBLE
        toolbar.visibility = View.VISIBLE
        spacer.visibility = checkVisibility(item, next)
        date.text = item.date.toRelative()
        root.setBackgroundResource(R.color.colorTransparent)
        when (item) {
            is BalanceEntity -> {
                categoryText.text = view.context.getString(item.account.getResource())
                value.text = item.value.toLocaleCurrency()
                date.visibility = View.GONE
                toolbar.visibility = View.GONE
                root.setBackgroundResource(R.color.colorIcon)
            }
            is IncomeEntity -> {
                categoryText.text = view.context.getString(item.category.getResource())
                value.text = item.value.toLocaleCurrency()
            }
            is ExpensesEntity -> {
                categoryText.text = view.context.getString(item.category.getResource())
                value.text = (-1 * item.value).toLocaleCurrency()
            }
        }
        toolbar.setOnMenuItemClickListener { view -> onMoreListener(view, position) }
    }

    private fun checkVisibility(item: TransactionEntity, next: TransactionEntity?): Int {
        return if (item is BalanceEntity || next == null || next is BalanceEntity)
            View.GONE
        else View.VISIBLE
    }


}


