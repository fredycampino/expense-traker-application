package com.campino.expenses.view

import com.campino.expenses.R
import com.campino.expenses.domine.AccountType
import com.campino.expenses.inject.InstanceProvides
import com.campino.expenses.tools.testInstrument
import com.campino.expenses.view.enumStrings
import com.campino.expenses.view.getResource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class StringExtKtTest{

    private val instruments: InstanceProvides = testInstrument()

    @Before
    fun before()  = runBlocking {
        instruments.deleteAppDatabase()
    }

    @Test
    fun enumValues(){
        val values =  enumStrings<AccountType> { item ->
             instruments.getString((item as AccountType).getResource())
        }
        Assert.assertEquals(instruments.getString(R.string.label_cash), values[0])
        Assert.assertEquals(instruments.getString(R.string.label_credit), values[1])
        Assert.assertEquals(instruments.getString(R.string.label_bank), values[2])
        Unit
    }

}