package zdz.libs.preferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.StateFactoryMarker
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import zdz.libs.preferences.annotations.ExperimentalPrefApi
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.PreferenceIOScope


val <T> Pref<T>.state
    @Composable
    get() = flow.collectAsState(default).value

@StateFactoryMarker
fun <T> Pref<T>.toMutalbeState(): MutableState<T> = object : MutableState<T> {
    override var value: T
        get() = runBlocking(PreferenceIOScope.coroutineContext) { current() }
        set(value) {
            PreferenceIOScope.launch { emit(value) }
        }

    override fun component1(): T = value

    override fun component2() = fun(it: T) { value = it }

    override fun toString(): String = name
}

val <T> Pref<T>.delegator: MutableState<T>
    @Composable
    get() = remember { mutableStateOf(default) }.apply { // rememberSaveable is not necessary
        LaunchedEffect(Unit) { // TODO: use lifecycle will cause an except emit of default value
            flow.collect { value = it }
        }
        LaunchedEffect(value) {
            withContext(PreferenceIOScope.coroutineContext) { emit(value) }
        }
    }

@ExperimentalPrefApi
val <T> Pref<List<T>>.delegator: SnapshotStateList<T>
    @Composable
    get() = SnapshotStateList<T>().apply {
        LaunchedEffect(Unit) {
            flow.collect {
                clear()
                addAll(it)
            }
        }
        LaunchedEffect(firstStateRecord) {
            withContext(PreferenceIOScope.coroutineContext) { emit(toList()) }
        }
    }

operator fun <T> Set<T>.rem(item: T) = if (item in this) this - item else this + item

operator fun <T> List<T>.rem(item: T) = if (item in this) this - item else this + item

@Deprecated("Use remAssign instead.", ReplaceWith("remAssign(item)"))
operator fun <T> MutableList<T>.rem(item: T) =
    if (item in this) this.remove(item) else this.add(item)

operator fun <T> MutableList<T>.remAssign(item: T) =
    if (item in this) this -= item else this += item