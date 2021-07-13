package com.campino.expenses.view

import androidx.annotation.MainThread
import androidx.lifecycle.*
import com.campino.expenses.domine.ErrorData
import com.campino.expenses.domine.ErrorRunning
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val onError = MutableLiveData<ErrorData>()
    fun onError(owner: LifecycleOwner, observer: (ErrorData) -> Unit) {
        onError.observe(owner, observer)
    }

    protected val onProgress = MutableLiveData<Boolean>()
    fun onProgress(owner: LifecycleOwner, observer: (Boolean) -> Unit) {
        onProgress.observe(owner, observer)
    }

    protected fun launchWithProgress(runWork: suspend () -> Unit) {
        viewModelScope.launch {
            onProgress.value = true
            try {
                runWork()
            } catch (e: Exception) {
                e.printStackTrace()
                onError.value = ErrorRunning(code=1,exception = e)
            } finally {
                onProgress.value = false
            }
        }
    }
}

/**
 * "This extension method is not required when using Kotlin 1.4. " +
 */
@MainThread
inline fun <T> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}



