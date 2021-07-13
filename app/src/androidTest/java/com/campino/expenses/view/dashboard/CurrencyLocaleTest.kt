package com.campino.expenses.view.dashboard

import com.campino.expenses.view.toLocaleCurrency
import org.junit.Assert
import org.junit.Test

class CurrencyLocaleTest {

    @Test
    fun currency_format_decimal() {
       val value:Float = 23.5f
       val format = value.toLocaleCurrency()
       Assert.assertTrue(format.contains("23,5"))
    }

    @Test
    fun currency_format_negative() {
        val value:Float = -23.5f
        val format = value.toLocaleCurrency()
        Assert.assertTrue(format.contains("-23,5"))
    }

}