package zdz.libs.preferences.model

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.runBlocking
import zdz.libs.preferences.annotations.ShadowedCoroutinesApi
import zdz.libs.preferences.contracts.Pref
import kotlin.coroutines.CoroutineContext

var PreferenceIOScope =
    CoroutineScope(Dispatchers.IO + CoroutineName("PreferenceIO") + SupervisorJob())
    private set

fun addExceptionHandler(handler: (CoroutineContext, Throwable) -> Unit) {
    PreferenceIOScope += CoroutineExceptionHandler(handler)
}

fun addContext(context: CoroutineContext) {
    PreferenceIOScope += context
}

@ShadowedCoroutinesApi
var <T> Pref<T>.value: T
    get() = get()
    set(value) {
        set(value).invokeOnCompletion {
            if (it == null) Log.v("Preferences", "Pref $name set value $value.")
            else Log.e("Preferences", "Pref $name set value $value failed", it)
        }
    }

@ShadowedCoroutinesApi
operator fun <T> Pref<T>.get(scope: CoroutineScope = PreferenceIOScope): T =
    runBlocking(scope.coroutineContext) { current() }

@ShadowedCoroutinesApi
fun <T> Pref<T>.getAsync(scope: CoroutineScope = PreferenceIOScope) =
    scope.async { current() }

@ShadowedCoroutinesApi
fun <T> Pref<T>.deleteAsync(scope: CoroutineScope = PreferenceIOScope) = scope.launch { delete() }

@ShadowedCoroutinesApi
fun <T> Pref<T>.set(value: T) = PreferenceIOScope.async { emit(value) }

@ShadowedCoroutinesApi
fun <T> Pref<T>.editAsync(block: suspend (T) -> T?) = PreferenceIOScope.async { edit(block) }

@ShadowedCoroutinesApi
fun <T> Pref<T>.resetAsync(scope: CoroutineScope = PreferenceIOScope) = scope.launch { reset() }


@ShadowedCoroutinesApi
fun <T> Pref<T>.use(scope: CoroutineScope = PreferenceIOScope, action: suspend (T) -> Unit) =
    scope.launch { action(current()) }

@ShadowedCoroutinesApi
fun <T, R> Pref<T>.run(scope: CoroutineScope = PreferenceIOScope, action: suspend (T) -> R): R =
    runBlocking(scope.coroutineContext) { action(current()) }