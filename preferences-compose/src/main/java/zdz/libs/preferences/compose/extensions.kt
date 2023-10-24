package zdz.libs.preferences.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.StateFactoryMarker
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import zdz.libs.preferences.contracts.Pref
import zdz.libs.preferences.model.PreferenceIOScope
import zdz.libs.preferences.model.get

val <T> Pref<T>.state
    @Composable
    get() = flow.collectAsStateWithLifecycle(default).value

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
    get() = rememberSaveable { mutableStateOf(default) }.apply {
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        LaunchedEffect(Unit) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect { value = it }
            }
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