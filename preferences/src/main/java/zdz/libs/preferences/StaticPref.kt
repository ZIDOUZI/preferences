package zdz.libs.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import zdz.libs.preferences.contracts.Pref

class StaticPref<T>(override val default: T) : Pref<T> {
    override val name: String = "static"
    private val _flow = MutableSharedFlow<T>()
    override val flow: Flow<T>
        get() = _flow
    
    override suspend fun delete() {
        reset()
    }
    
    override suspend fun edit(block: suspend (T) -> T?): T? =
        block(_flow.replayCache.first()).apply {
            this?.let { _flow.emit(it) } ?: reset()
        }
}