package com.campino.expenses.domine

import java.io.IOException
import java.lang.Exception

interface ErrorData {
    val code: Int
    val exception:Exception
}

class ErrorRunning(
    override val code: Int, override val exception: Exception
):ErrorData
