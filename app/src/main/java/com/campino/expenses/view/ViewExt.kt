package com.campino.expenses.view

import android.app.Activity
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar


fun ProgressBar.setActive(active: Boolean) {
    visibility = if (active) {
        View.VISIBLE
    } else {
        View.GONE
    }
    isIndeterminate = active
}


fun Activity.getRootView(): View {
    return findViewById<View>(android.R.id.content)
}

interface AfterTextChanged : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

