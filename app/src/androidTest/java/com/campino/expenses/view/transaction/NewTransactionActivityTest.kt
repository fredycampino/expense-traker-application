package com.campino.expenses.view.transaction

import com.campino.expenses.inject.InstanceProvides
import com.campino.expenses.tools.launchActivity
import com.campino.expenses.tools.testInstrument
import com.campino.expenses.view.transaction.NewTransactionActivity
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewTransactionActivityTest {

    private val instruments: InstanceProvides = testInstrument()

    @Before
    fun before()  = runBlocking {
        instruments.deleteAppDatabase()
    }

    @Test
    fun newTransactionActivity()   = runBlocking{
        launchActivity<NewTransactionActivity>()
        Unit // break point here for launch the activity in isolation
    }

}