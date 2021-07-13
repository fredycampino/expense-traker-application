package com.campino.expenses.view.transaction

import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.activity.viewModels
import com.campino.expenses.BaseActivity
import com.campino.expenses.R
import com.campino.expenses.domine.*
import com.campino.expenses.inject.DaggerApp
import com.campino.expenses.view.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_new_transaction.*
import javax.inject.Inject

class NewTransactionActivity : BaseActivity(), AfterTextChanged {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var toggleButtonGroup: MaterialButtonToggleGroup
    private lateinit var categoryAuto: AutoCompleteTextView
    private lateinit var accountAuto: AutoCompleteTextView
    private lateinit var valueText: TextInputEditText

    @Inject
    lateinit var factory: NewTransactionFactory
    private val viewModel: NewTransactionViewModel by viewModels { factory }
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_transaction)
        progress = findViewById(R.id.transactionProgress)
        categoryAuto = findViewById(R.id.categoryAuto)
        accountAuto = findViewById(R.id.accountAuto)

        bindToolbar()
        bindAccounts()
        binTransactionType()
        bindCategory(isIncome())
        binValue()

        viewModel.onStatus(this) {
            onFormStatus(it)
        }

        viewModel.onProgress(this) {
            progress.setActive(it)
        }
    }


    override fun onStart() {
        super.onStart()
        notifyFormChange()
    }

    private fun binValue() {
        valueText = findViewById(R.id.transactionValue)
        valueText.addTextChangedListener(this)
    }

    override fun afterTextChanged(it: Editable?) {
        notifyFormChange()
    }

    private fun bindAccounts() {
        val listSymbols = enumStrings<AccountType> {
            getString((it as AccountType).getResource())
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.item_auto, listSymbols)
        accountAuto.setAdapter(adapter)
        accountAuto.setOnItemClickListener { _, _, _, _ ->
            notifyFormChange()
        }
    }

    private fun bindCategory(income: Boolean = isIncome()) {
        categoryAuto.setText("")
        val listSymbols = if (income) {
            enumStrings<IncomeCategory> {
                getString((it as IncomeCategory).getResource())
            }
        } else {
            enumStrings<ExpenseCategory> {
                getString((it as ExpenseCategory).getResource())
            }
        }

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, R.layout.item_auto, listSymbols)
        categoryAuto.setAdapter(adapter)
        categoryAuto.setOnItemClickListener { _, _, _, _ ->
            notifyFormChange()
        }
    }

    private fun binTransactionType() {
        toggleButtonGroup = findViewById(R.id.transactionToggleButton)
        toggleButtonGroup.addOnButtonCheckedListener { _, _, _ ->
            bindCategory(isIncome())
            notifyFormChange()
        }
    }

    private fun getForm(): FormTransaction {
        val account: AccountType? = accountAuto.text.toString().toAccountType(this)
        val income = isIncome()
        val category = categoryAuto.text.toString()
        val categoryType = when {
            category.isEmpty() -> null
            income -> category.toIncomeCategory(this)
            else -> category.toExpenseCategory(this)
        }

        return FormTransaction(
            income,
            account,
            categoryType,
            valueText.text.toString()
        )
    }

    override fun inject() {
        (application as DaggerApp).appComponent
            .getTransactionComponent()
            .inject(this)
    }

    private fun bindToolbar() {
        toolbar = findViewById<MaterialToolbar>(R.id.dashboardToolbar)
        toolbar.setOnMenuItemClickListener {
            onClickAction(it)
            return@setOnMenuItemClickListener true
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun notifyFormChange() {
        viewModel.formChange(getForm())
    }

    private fun isIncome(): Boolean {
        return toggleButtonGroup.checkedButtonId == R.id.buttonIncome
    }

    /***************************   Observers *************************************************/
    private fun onClickAction(menuItem: MenuItem?) {
        viewModel.create(getForm())
    }

    private fun onFormStatus(it: FormStatus) {
        val done = toolbar.menu.findItem(R.id.actionDone)
        when (it) {
            FormStatus.TODO -> done.isEnabled = true
            FormStatus.INCOMPLETE -> done.isEnabled = false
            FormStatus.DONE -> {
                done.isEnabled = false
                finish()
            }
        }
    }


}