package com.campino.expenses.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.campino.expenses.BaseActivity
import com.campino.expenses.R
import com.campino.expenses.domine.TransactionEntity
import com.campino.expenses.inject.DaggerApp
import com.campino.expenses.view.getRootView
import com.campino.expenses.view.setActive
import com.campino.expenses.view.transaction.NewTransactionActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_dash_board.*
import javax.inject.Inject

class DashBoardActivity : BaseActivity() {
    private lateinit var transactionAdapter: TransactionAdapter

    @Inject
    lateinit var viewModelFactory: DashBoardFactory
    private val viewModel: DashBoardViewModel by viewModels { viewModelFactory }

    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        progress = findViewById(R.id.transactionProgress)
        binAdapter()
        bindToolbar()
        newTransactionFab.setOnClickListener {
            launchNewTransaction()
        }

        viewModel.onTransactions(this) {
            onTransactionFetch(it)
        }

        viewModel.onProgress(this) {
            progress.setActive(it)
        }

        viewModel.onError(this){
            Snackbar.make(getRootView(),
                getString(R.string.error_error_in_server,it.code),
                Snackbar.LENGTH_LONG)
                .show()
        }
    }

    override fun inject() {
        (application as DaggerApp).appComponent
            .getDashBoardComponent()
            .inject(this)
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetch()
    }

    private fun bindToolbar() {
        val toolbar = findViewById<MaterialToolbar>(R.id.dashboardToolbar)
        toolbar.setOnMenuItemClickListener {
            onClickAction(it)
            return@setOnMenuItemClickListener true
        }
    }

    private fun binAdapter() {
        transactionAdapter = TransactionAdapter()
        val recyclerView = findViewById<RecyclerView>(R.id.listTransactions)
        val viewManager = LinearLayoutManager(this)
        recyclerView.adapter = transactionAdapter
        recyclerView.layoutManager = viewManager

        transactionAdapter.setOnClickAction { menuItem: MenuItem?, t: TransactionEntity ->
            onClickAction(menuItem, t)
        }
    }

    private fun onTransactionFetch(list: List<TransactionEntity>) {
        transactionAdapter.update(list)
    }

    private fun onClickAction(menuItem: MenuItem?, t: TransactionEntity? = null) {
        when (menuItem?.itemId) {
            R.id.actionDelete -> viewModel.delete(t!!)
            R.id.actionNewTransaction,
            R.id.newTransactionFab -> launchNewTransaction()
        }
    }

    private fun launchNewTransaction() {
        val intent = Intent(this, NewTransactionActivity::class.java)
        startActivity(intent)
    }

}