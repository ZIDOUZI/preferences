package zdz.libs.preferences.contracts

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface Pref<T> {
    val flow: Flow<T>
    val name: String
    val default: T
    suspend fun current(): T = flow.first() // how? why?
    suspend fun delete()
    suspend fun emit(value: T): T? = edit { value }
    suspend fun edit(block: suspend (T) -> T?): T?
    suspend fun reset() = emit(default)
}