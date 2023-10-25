package zdz.libs.preferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.StateFactoryMarker
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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

val <T> Pref<T>.mutableFlow: MutableStateFlow<T>
    get() = object : StateFlow<T>, MutableStateFlow<T> by getMutableFlow() {
        override suspend fun emit(value: T) {
            this@mutableFlow.emit(value)
        }
        
        override fun compareAndSet(expect: T, update: T): Boolean = if (value == expect) {
            value = update
            true
        } else false
        
        override fun tryEmit(value: T): Boolean = runBlocking(PreferenceIOScope.coroutineContext) {
            emit(value)
            true
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
@JvmName("private_function")
private fun <T> Pref<T>.getMutableFlow(): MutableStateFlow<T> = MutableStateFlow(default).apply flow@{
    PreferenceIOScope.launch(start = CoroutineStart.UNDISPATCHED) {
        SharingStarted.WhileSubscribed(5000).command(subscriptionCount)
            .distinctUntilChanged<SharingCommand>()
            .collectLatest<SharingCommand> {
                when (it) {
                    SharingCommand.START -> flow.collect(this@flow)
                    SharingCommand.STOP -> {}
                    SharingCommand.STOP_AND_RESET_REPLAY_CACHE -> resetReplayCache()
                }
            }
    }
    
}

operator fun <T> Set<T>.rem(item: T) = if (item in this) this - item else this + item

operator fun <T> List<T>.rem(item: T) = if (item in this) this - item else this + item

@Deprecated("Use remAssign instead.", ReplaceWith("remAssign(item)"))
operator fun <T> MutableList<T>.rem(item: T) =
    if (item in this) this.remove(item) else this.add(item)

operator fun <T> MutableList<T>.remAssign(item: T) =
    if (item in this) this -= item else this += item